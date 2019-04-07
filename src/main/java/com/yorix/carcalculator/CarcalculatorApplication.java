package com.yorix.carcalculator;

import com.yorix.carcalculator.servise.ImageStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@SpringBootApplication
public class CarcalculatorApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(CarcalculatorApplication.class, args);

        Runtime.
                getRuntime().
                exec("cmd /c \"c:/Program Files/carcalculator/show.bat\"");
    }

    @Bean
    CommandLineRunner init(ImageStorageService imageStorageService) {
        return (args) -> {
//            imageStorageService.deleteAll();
            imageStorageService.init();
        };
    }
}
