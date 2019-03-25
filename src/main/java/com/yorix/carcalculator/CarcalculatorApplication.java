package com.yorix.carcalculator;

import com.yorix.carcalculator.servise.ImageStorageService;
import com.yorix.carcalculator.storage.StorageProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
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
