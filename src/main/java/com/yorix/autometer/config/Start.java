package com.yorix.autometer.config;

import com.yorix.autometer.errors.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class Start {
    private String updateAns;
    private String rootLocation;
    private String dbBackupLocation;
    private AppProperties properties;
    @Value("${app.default-image-full-filename}")
    private Resource resource;

    @Autowired
    public Start(AppProperties properties) {
        this.properties = properties;
        this.rootLocation = Paths.get(URI.create("file://" .concat(properties.getRootLocation()))).toString();
        this.dbBackupLocation = Paths.get(URI.create("file://" .concat(properties.getDbBackupLocation()))).toString();

    }

    @PostConstruct
    public void init() throws IOException, InterruptedException {
        Path storageLocation = Paths.get(URI.create(
                "file://" .concat(properties.getImageStorageLocation())
        ));
        Path outputFilepath = storageLocation.resolve(resource.getFilename());

        try {
            Files.createDirectories(storageLocation);
            try (InputStream is = new BufferedInputStream(resource.getInputStream());
                 OutputStream os = new BufferedOutputStream(Files.newOutputStream(outputFilepath))) {
                os.write(is.readAllBytes());
            }
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }

        Path dbBackupPath = Paths.get(URI.create(
                "file://" .concat(properties.getDbBackupLocation())
        ));
        Files.createDirectories(dbBackupPath);

        saveData();

        String command = String.format("cmd /c cd /d \"%s\" && git pull>.gitAns", rootLocation);
        Runtime.getRuntime()
                .exec(command)
                .waitFor();

        checkUpdate(new File(rootLocation + "/.gitAns"));
        Runtime.getRuntime().exec("cmd /c explorer http://localhost:8080/");
    }

    private void saveData() {
        String command = String.format(
                "cmd /c mysqldump -uuser -puser autometer > %s/autometer_%s.sql",
                dbBackupLocation,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy-hh.mm.ss")));
        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File backupDir = new File("file://" .concat(dbBackupLocation));
        File[] files = backupDir.listFiles();
        if ((files != null ? files.length : 0) > 100) {
            files[0].delete();
        }
    }

    public void readData(String filename) {
        String command = String.format(
                "cmd /c mysql -uuser -puser autometer < %s/%s",
                dbBackupLocation,
                filename);
        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void installUpdate() {
        String command = "cmd /c " +
                "cd /d \"" + rootLocation + "\" && " +
                "SetLocal EnableExtensions && " +
                "SET ProcessName=javaw.exe && " +
                "TaskList /FI \"ImageName EQ %ProcessName%\" | Find /I \"%ProcessName%\" && " +
                "IF NOT %ERRORLEVEL% NEQ 0 (taskkill /IM javaw.exe /f) && " +
                "call mvn package -am -o -Dmaven.test.skip -T 1C && " +
                "run.cmd";
        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkUpdate(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        updateAns = new String(is.readAllBytes());
    }

    public String getUpdateAns() {
        return updateAns;
    }
}
