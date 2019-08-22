package com.yorix.autometer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app")
@Data
public class AppProperties {
    private String shell = "bash -c";
    private String dbBackupLocation;
    private String imageStorageLocation;
    private String defaultImageFilename = "00_default.png";
    private String defaultImageFullFilename = "classpath:/static/media/" + defaultImageFilename;
    private String currencyUrl = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange";
    private String currencyUrlParams = "?valcode=%s&date=%s&json";
    private String[] currencyCodes = {"usd", "eur"};
}
