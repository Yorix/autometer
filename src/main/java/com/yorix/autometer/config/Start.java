package com.yorix.autometer.config;

import com.yorix.autometer.errors.StorageException;
import com.yorix.autometer.model.Lot;
import com.yorix.autometer.model.Param;
import com.yorix.autometer.model.Role;
import com.yorix.autometer.model.User;
import com.yorix.autometer.service.AuctionService;
import com.yorix.autometer.service.UserService;
import com.yorix.autometer.storage.ParamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class Start {
    private final String dbBackupLocation;
    private final String imgStorageLocation;
    private final ParamRepository paramRepository;
    private final UserService userService;
    private final AuctionService auctionService;
    private final String adminUsername;
    private final String adminPassword;
    private final long ipsClearTimeSec;
    private final String defaultCar;
    private final String defaultUser;
    private final String providerPath;

    @Autowired
    public Start(
            AppProperties properties,
            ParamRepository paramRepository,
            UserService userService,
            AuctionService auctionService
    ) {
        dbBackupLocation = properties.getDbBackupLocation();
        imgStorageLocation = properties.getImageStorageLocation();
        adminUsername = properties.getAdminUsername();
        adminPassword = properties.getAdminPassword();
        ipsClearTimeSec = properties.getIpsClearTimeSec();
        defaultCar = properties.getDefaultCarImageFilename();
        defaultUser = properties.getDefaultUserImageFilename();
        providerPath = properties.getProviderPath();
        this.paramRepository = paramRepository;
        this.userService = userService;
        this.auctionService = auctionService;
    }

    @PostConstruct
    public void init() throws IOException {
        Files.createDirectories(Paths.get(dbBackupLocation));
        Files.createDirectories(Paths.get(imgStorageLocation));

        ApplicationContext context = new ClassPathXmlApplicationContext();
        Resource carResource = context.getResource(providerPath.concat(defaultCar));
        Resource userResource = context.getResource(providerPath.concat(defaultUser));

        InputStream defaultCarIs = carResource.getInputStream();
        InputStream defaultUserIs = userResource.getInputStream();
        OutputStream defaultCarOs = new FileOutputStream(imgStorageLocation.concat(defaultCar));
        OutputStream defaultUserOs = new FileOutputStream(imgStorageLocation.concat(defaultUser));

        try (InputStream carIs = new BufferedInputStream(defaultCarIs);
             InputStream userIs = new BufferedInputStream(defaultUserIs);
             OutputStream carOs = new BufferedOutputStream(defaultCarOs);
             OutputStream userOs = new BufferedOutputStream(defaultUserOs)) {
            carOs.write(carIs.readAllBytes());
            userOs.write(userIs.readAllBytes());
        } catch (IOException e) {
            throw new StorageException("Could not copy default image", e);
        }

        paramRepository.findById("budget")
                .orElseGet(() -> paramRepository.save(new Param("budget", 0)));

        createFirstUser();
        startCleanIpsSchedule();
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

    private void startCleanIpsSchedule() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                List<Lot> lots = auctionService.readAll();
                lots.forEach(auctionService::clearIps);
            }
        };

        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(timerTask, 0, ipsClearTimeSec * 1000);
    }
}
