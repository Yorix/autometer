package com.yorix.autometer;

import com.yorix.autometer.service.ImageStorageService;
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
                exec("cmd /c \"c:/Program Files/autometer/show.bat\"");
    }

    @Bean
    CommandLineRunner init(ImageStorageService imageStorageService) {
        return (args) -> {
//            imageStorageService.deleteAll();
            imageStorageService.init();
        };
    }
}
