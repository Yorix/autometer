package com.yorix.autometer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app.text.card")
@Data
public class CardTextProperties {
    private String header;
    private String body;
    private String footer;
}
