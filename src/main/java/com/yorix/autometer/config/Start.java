package com.yorix.autometer.config;

import com.yorix.autometer.errors.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class Start {
    private String dbBackupLocation;
    private AppProperties properties;
    private DataSourceProperties dataSourceProperties;
    @Value("${app.default-image-full-filename}")
    private Resource resource;

    @Autowired
    public Start(AppProperties properties, DataSourceProperties dataSourceProperties) {
        this.properties = properties;
        this.dataSourceProperties = dataSourceProperties;
        this.dbBackupLocation = Paths.get(properties.getDbBackupLocation()).toString();

    }

    @PostConstruct
    public void init() throws IOException {
        Path storageLocation = Paths.get(properties.getImageStorageLocation());
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

        Path dbBackupPath = Paths.get(properties.getDbBackupLocation());
        Files.createDirectories(dbBackupPath);

        saveData();
    }

    private void saveData() {
        String command = String.format(
                "mysqldump -u%s -p%s autometer > %s/autometer_%s.sql",
                dataSourceProperties.getUsername(),
                dataSourceProperties.getPassword(),
                dbBackupLocation,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy-hh.mm.ss")));
        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File backupDir = new File(dbBackupLocation);
        File[] files = backupDir.listFiles();
        if ((files != null ? files.length : 0) > 100) {
            files[0].delete();
        }
    }

    public void readData(String filename) {
        String command = String.format(
                "mysql -u%s -p%s autometer < %s/%s",
                dataSourceProperties.getUsername(),
                dataSourceProperties.getPassword(),
                dbBackupLocation,
                filename);
        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
