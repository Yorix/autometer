package com.yorix.autometer.service;

import com.yorix.autometer.config.AppProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class DbService {
    private final AppProperties appProperties;
    private final DataSourceProperties dataSourceProperties;

    public DbService(DataSourceProperties dataSourceProperties, AppProperties appProperties) {
        this.dataSourceProperties = dataSourceProperties;
        this.appProperties = appProperties;
    }

    public void saveData() {
        String[] command = {
                appProperties.getShell(),
                appProperties.getShellArg(),
                "mysqldump -u" + dataSourceProperties.getUsername() + " -p" + dataSourceProperties.getPassword()
                        + " autometer > " + appProperties.getDbBackupLocation() + "/autometer_"
                        + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd-hh.mm.ss")) + ".sql"
        };

        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File backupDir = new File(appProperties.getDbBackupLocation());
        File[] files = backupDir.listFiles();
        if ((files != null ? files.length : 0) > 500) {
            files[0].delete();
        }
    }
}
