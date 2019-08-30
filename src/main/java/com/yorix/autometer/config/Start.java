package com.yorix.autometer.config;

import com.yorix.autometer.errors.StorageException;
import com.yorix.autometer.model.Param;
import com.yorix.autometer.model.Role;
import com.yorix.autometer.model.User;
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
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class Start {
    private final String shell;
    private final String dbBackupLocation;
    private final String imgStorageLocation;
    private final DataSourceProperties dataSourceProperties;
    private final ParamRepository paramRepository;
    private final UserService userService;
    private final String adminUsername;
    private final String adminPassword;
    @Value("${app.default-image-full-filename}")
    private Resource resource;

    @Autowired
    public Start(
            AppProperties properties,
            DataSourceProperties dataSourceProperties,
            ParamRepository paramRepository,
            UserService userService
    ) {
        shell = properties.getShell();
        dbBackupLocation = properties.getDbBackupLocation();
        imgStorageLocation = properties.getImageStorageLocation();
        adminUsername = properties.getAdminUsername();
        adminPassword = properties.getAdminPassword();
        this.dataSourceProperties = dataSourceProperties;
        this.paramRepository = paramRepository;
        this.userService = userService;
    }

    @PostConstruct
    public void init() throws IOException {
        Path storageLocation = Paths.get(imgStorageLocation);
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

        Files.createDirectories(Paths.get(dbBackupLocation));

        paramRepository.findById("budget")
                .orElseGet(() -> paramRepository.save(new Param("budget", 0)));

        createFirstUser();
        saveData();
    }

    private void createFirstUser() {
        if (userService.readAll().size() == 0) {
            User admin = new User();
            admin.setUsername(adminUsername);
            admin.setPassword(adminPassword);
            admin.setRoles(Collections.singleton(Role.ADMIN));
            userService.create(admin);
        }
    }

    private void saveData() {
        String command = String.format(
                "%s mysqldump -u%s -p%s autometer > %s/autometer_%s.sql",
                shell,
                dataSourceProperties.getUsername(),
                dataSourceProperties.getPassword(),
                dbBackupLocation,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd-hh.mm.ss")));
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
                "%s mysql -u%s -p%s autometer < %s/%s",
                shell,
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
