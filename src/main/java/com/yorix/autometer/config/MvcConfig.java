package com.yorix.autometer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    private String storage;

    @Autowired
    public MvcConfig(AppProperties properties) {
        this.storage = Paths.get(properties.getImageStorageLocation()).toString();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/img/*")
                .addResourceLocations("file:/" .concat(storage).concat(File.separator));
    }
}
