package com.yorix.autometer.service;

import com.yorix.autometer.model.Car;
import com.yorix.autometer.model.Img;
import com.yorix.autometer.storage.ImgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ImgService {
    private final ImgRepository imgRepository;
    private final ImageStorageService imageStorageService;

    @Autowired
    public ImgService(ImgRepository imgRepository, ImageStorageService imageStorageService) {
        this.imgRepository = imgRepository;
        this.imageStorageService = imageStorageService;
    }

    public Img create(MultipartFile file, Car car) {
        Img img = new Img();
        img.setFilename(file.getOriginalFilename());
        img.setCar(car);
        imageStorageService.store(file);
        return imgRepository.save(img);
    }

    public Img read(String filename) {
        return imgRepository.getOne(filename);
    }

    public List<Img> readAllByCar(Car car) {
        return imgRepository.readAllByCar(car);
    }

    public void delete(Img img) {
        imgRepository.delete(img);
    }

    public void deleteAllByCar(Car car) {
        imgRepository.deleteAllByCar(car);
    }
}
