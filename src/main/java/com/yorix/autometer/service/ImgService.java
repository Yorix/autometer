package com.yorix.autometer.service;

import com.yorix.autometer.config.AppProperties;
import com.yorix.autometer.errors.StorageException;
import com.yorix.autometer.model.Car;
import com.yorix.autometer.model.Img;
import com.yorix.autometer.storage.ImgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class ImgService extends AppService {
    private final ImgRepository imgRepository;

    @Autowired
    public ImgService(ImgRepository imgRepository) {
        this.imgRepository = imgRepository;
    }

    public void create(MultipartFile file, Car car) {
        String filename = file.getOriginalFilename();
        if (filename == null || filename.equals(""))
            throw new StorageException("Файл не выбран.");
        Img img = imgRepository.readByFilename(filename);
        if (img != null)
            throw new StorageException("Файл с таким именем уже существует.");
        img = new Img();
        img.setFilename(filename);
        img.setCar(car);
        String path = getProperties().getImageStorageLocation()
                .concat(File.separator)
                .concat(filename);
        try {
            file.transferTo(new File(path));
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
        imgRepository.save(img);
    }

    public Img read(String filename) {
        return imgRepository.getOne(filename);
    }

    public List<Img> readAllByCar(Car car) {
        return imgRepository.readAllByCar(car);
    }

    public void delete(String filename, Car car) {
        if (filename.equals(car.getImgFilename())) {
            car.setImgFilename(getProperties().getDefaultImageFilename());
        }

        File file = new File(getProperties().getImageStorageLocation()
                .concat(File.separator)
                .concat(filename));
        file.delete();
        imgRepository.deleteById(filename);
    }
}
