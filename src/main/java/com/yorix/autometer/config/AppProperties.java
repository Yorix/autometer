package com.yorix.autometer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app")
@Data
public class AppProperties {
    private String rootLocation = System.getProperty("user.dir");
    private String dbBackupLocation = rootLocation + "/../backup";
    private String imageStorageLocation = rootLocation + "/../images/";
    private String defaultImageLocation;
    private String defaultImageFilename;
    private String currencyUrl;
}
