package com.yorix.autometer.config;

import com.yorix.autometer.errors.StorageException;
import com.yorix.autometer.model.Param;
import com.yorix.autometer.model.Role;
import com.yorix.autometer.model.User;
import com.yorix.autometer.service.UserService;
import com.yorix.autometer.storage.ParamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

@Component
public class Start {
    private final String dbBackupLocation;
    private final String imgStorageLocation;
    private final ParamRepository paramRepository;
    private final UserService userService;
    private final String adminUsername;
    private final String adminPassword;
    @Value("${app.default-image-full-filename}")
    private Resource defaultImage;

    @Autowired
    public Start(
            AppProperties properties,
            ParamRepository paramRepository,
            UserService userService
    ) {
        dbBackupLocation = properties.getDbBackupLocation();
        imgStorageLocation = properties.getImageStorageLocation();
        adminUsername = properties.getAdminUsername();
        adminPassword = properties.getAdminPassword();
        this.paramRepository = paramRepository;
        this.userService = userService;
    }

    @PostConstruct
    public void init() throws IOException {
        Path storageLocation = Paths.get(imgStorageLocation);
        Path outputFilepath = storageLocation.resolve(defaultImage.getFilename());

        Files.createDirectories(Paths.get(dbBackupLocation));
        Files.createDirectories(storageLocation);

        try (InputStream is = new BufferedInputStream(defaultImage.getInputStream());
             OutputStream os = new BufferedOutputStream(Files.newOutputStream(outputFilepath))) {
            os.write(is.readAllBytes());
        } catch (IOException e) {
            throw new StorageException("Could not copy default image", e);
        }

        paramRepository.findById("budget")
                .orElseGet(() -> paramRepository.save(new Param("budget", 0)));

        createFirstUser();
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
}
