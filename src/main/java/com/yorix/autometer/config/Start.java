package com.yorix.autometer.config;

import com.yorix.autometer.errors.StorageException;
import com.yorix.autometer.model.Param;
import com.yorix.autometer.service.UserService;
import com.yorix.autometer.storage.ParamRepository;
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
    private final String dbBackupLocation;
    private final AppProperties properties;
    private final DataSourceProperties dataSourceProperties;
    private final ParamRepository paramRepository;
    private final UserService userService;
    @Value("${app.default-image-full-filename}")
    private Resource resource;

    @Autowired
    public Start(
            AppProperties properties,
            DataSourceProperties dataSourceProperties,
            ParamRepository paramRepository,
            UserService userService
    ) {
        this.properties = properties;
        this.dataSourceProperties = dataSourceProperties;
        this.dbBackupLocation = Paths.get(properties.getDbBackupLocation()).toString();
        this.paramRepository = paramRepository;
        this.userService = userService;
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

        paramRepository.findById("budget")
                .orElseGet(() -> paramRepository.save(new Param("budget", 0)));

        if (userService.readAll().size() == 0) {
            //TODO !!!!!!!!!!!!!!!!!
        }
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
