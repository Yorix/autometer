package com.yorix.carcalculator;

import com.yorix.carcalculator.servise.ImageStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.TimeZone;

@SpringBootApplication
public class CarcalculatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarcalculatorApplication.class, args);
    }

    @Bean
    CommandLineRunner init(ImageStorageService imageStorageService) {
        return (args) -> {
//            imageStorageService.deleteAll();
            imageStorageService.init();
        };
    }
}
