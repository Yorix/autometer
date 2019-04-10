package com.yorix.carcalculator.service;

import com.yorix.carcalculator.config.StorageProperties;
import com.yorix.carcalculator.errors.StorageException;
import com.yorix.carcalculator.errors.StorageFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class FileSystemImageStorageService implements ImageStorageService {
    private final StorageProperties properties;
    private final Path rootLocation;
    private final ResourceLoader resourceLoader;

    @Autowired
    public FileSystemImageStorageService(StorageProperties properties, ResourceLoader resourceLoader) {
        this.properties = properties;
        this.rootLocation = Paths.get(properties.getLocation());
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void store(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        Path fullPath = this.rootLocation.resolve(filename);

        if (file.isEmpty() && (filename == null || filename.length() == 0)) {
            return;
        }
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream is = new BufferedInputStream(file.getInputStream());
                 OutputStream os = new BufferedOutputStream(Files.newOutputStream(fullPath))) {
                os.write(is.readAllBytes());
            }
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void delete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Could not delete file: " + path.getFileName(), e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        String sourceImageLocation = properties.getDefaultImageLocation();
        String sourceImageFilename = properties.getDefaultImageFilename();
        String sourceImageFullName = sourceImageLocation + sourceImageFilename;

        Resource resource = resourceLoader.getResource(sourceImageFullName);
        Path outputFilepath = this.rootLocation.resolve(resource.getFilename());

        try {
            Files.createDirectories(rootLocation);
            try (InputStream is = new BufferedInputStream(resource.getInputStream());
                 OutputStream os = new BufferedOutputStream(Files.newOutputStream(outputFilepath))) {
                os.write(is.readAllBytes());
            }
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}