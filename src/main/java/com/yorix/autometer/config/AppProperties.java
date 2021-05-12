package com.yorix.autometer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app")
@Data
public class AppProperties {
    private String shell;
    private String shellArg;
    private String dbBackupLocation;
    private String dbFilenameTimeFormat;
    private String imageStorageLocation;
    private String defaultCarImageFilename = "d_car.png";
    private String defaultUserImageFilename = "d_user.png";
    private String providerPath = "classpath:/static/media/";
    private String auctionUrl;
    private String currencyUrl = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange";
    private String currencyUrlParams = "?valcode=%s&date=%s&json";
    private String[] currencyCodes = {"usd", "eur"};
    private String adminUsername;
    private String adminPassword;
    private String mailTo;
    private long ipsClearTimeSec;
}
