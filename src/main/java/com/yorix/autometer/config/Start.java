package com.yorix.autometer.config;

import com.yorix.autometer.service.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class Start {
    private final ImageStorageService imageStorageService;
    private String updateAns;
    private String rootLocation;
    private String dbBackupLocation;

    @Autowired
    public Start(AppProperties properties, ImageStorageService imageStorageService) {
        this.imageStorageService = imageStorageService;
        this.rootLocation = properties.getRootLocation();
        this.dbBackupLocation = properties.getDbBackupLocation();
    }

    @PostConstruct
    public void init() throws IOException, InterruptedException {
        imageStorageService.init();
        Files.createDirectories(Path.of(dbBackupLocation));
        saveData();

        String command = String.format("cmd /c cd /d \"%s\" && git pull>.gitAns", rootLocation);
        Runtime.getRuntime().exec(command).waitFor();
        checkUpdate(new File(rootLocation + "/.gitAns"));
        Runtime.getRuntime().exec("cmd /c explorer http://localhost:8080/");
    }

    public void saveData() {
        String command = String.format(
                "cmd /c mysqldump -uuser -puser autometer > %s/autometer_%s.sql",
                dbBackupLocation,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy-hh.mm.ss")));
        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File backupDir = new File(dbBackupLocation);
        File[] files = backupDir.listFiles();
        assert files != null;
        if (files.length > 100) {
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
