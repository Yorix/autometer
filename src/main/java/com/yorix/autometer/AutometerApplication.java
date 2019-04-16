package com.yorix.autometer;

import com.yorix.autometer.service.ImageStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class AutometerApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(AutometerApplication.class, args);

        String filepath = new File("show.cmd").getAbsolutePath();
        Runtime.getRuntime().exec("cmd /c \"" + filepath + "\"");
    }

    @Bean
    CommandLineRunner init(ImageStorageService imageStorageService) {
        return (args) -> {
//            imageStorageService.deleteAll();
            imageStorageService.init();
        };
    }
}
