package com.yorix.autometer.service;

import com.yorix.autometer.config.AppProperties;
import com.yorix.autometer.errors.StorageException;
import com.yorix.autometer.errors.StorageFileNotFoundException;
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
    private final AppProperties properties;
    private final Path storageLocation;
    private final ResourceLoader resourceLoader;

    @Autowired
    public FileSystemImageStorageService(AppProperties properties, ResourceLoader resourceLoader) {
        this.properties = properties;
        this.storageLocation = Paths.get(properties.getImageStorageLocation());
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void store(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        Path fullPath = this.storageLocation.resolve(filename);

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
            return Files.walk(this.storageLocation, 1)
                    .filter(path -> !path.equals(this.storageLocation))
                    .map(this.storageLocation::relativize);
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    @Override
    public Path load(String filename) {
        return storageLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);
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
        FileSystemUtils.deleteRecursively(storageLocation.toFile());
    }

    @Override
    public void init() {
        String sourceImageLocation = properties.getDefaultImageLocation();
        String sourceImageFilename = properties.getDefaultImageFilename();
        String sourceImageFullName = sourceImageLocation + sourceImageFilename;
        Resource resource = resourceLoader.getResource(sourceImageFullName);
        Path outputFilepath = storageLocation.resolve(resource.getFilename());

        try {
            Files.createDirectories(storageLocation);
            try (InputStream is = new BufferedInputStream(resource.getInputStream());
                 OutputStream os = new BufferedOutputStream(Files.newOutputStream(outputFilepath))) {
                os.write(is.readAllBytes());
            }
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}