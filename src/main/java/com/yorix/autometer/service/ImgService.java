package com.yorix.autometer.service;

import com.yorix.autometer.errors.StorageException;
import com.yorix.autometer.model.Car;
import com.yorix.autometer.model.Img;
import com.yorix.autometer.storage.CarRepository;
import com.yorix.autometer.storage.ImgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

@Service
public class ImgService extends AppService {
    private final ImgRepository imgRepository;
    private final CarRepository carRepository;

    @Autowired
    public ImgService(ImgRepository imgRepository, CarRepository carRepository) {
        this.imgRepository = imgRepository;
        this.carRepository = carRepository;
    }

    public void create(String imgUrl, Car car) {
        Img img = saveImg(car);
        String filepath = getFilepath(car, img);

        File file = new File(filepath);
        try {
            URL url = new URL(imgUrl);
            BufferedImage image = ImageIO.read(url);
            ImageIO.write(image, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        saveData();
    }

    public void create(MultipartFile file, Car car) {
        if (StringUtils.isEmpty(file.getOriginalFilename()))
            throw new StorageException("Файл не выбран.");

        Img img = saveImg(car);
        String filepath = getFilepath(car, img);

        try {
            file.transferTo(new File(filepath));
        } catch (IOException e) {
            throw new StorageException("Ошибка загрузки файла " + file.getOriginalFilename(), e);
        }

        saveData();
    }

    private Img saveImg(Car car) {
        Img img = new Img();
        img.setCar(car);
        imgRepository.save(img);
        return img;
    }

    private String getFilepath(Car car, Img img) {
        return getAppProperties().getImageStorageLocation()
                .concat(File.separator)
                .concat(Integer.toString(car.getId()))
                .concat("_")
                .concat(Integer.toString(img.getId()))
                .concat(".jpg");
    }

    public Img read(int id) {
        return imgRepository.getOne(id);
    }

    public List<Img> readAllByCar(Car car) {
        return imgRepository.readAllByCar(car);
    }

    public void delete(int id, Car car) {
        if (id == Integer.parseInt(car.getCurrentImg().replaceAll("^\\d+_|.jpg$|.png$", ""))) {
            car.setCurrentImg(getAppProperties().getDefaultImageFilename());
            carRepository.save(car);
        }

        File file = new File(getAppProperties().getImageStorageLocation()
                .concat(File.separator)
                .concat(Integer.toString(car.getId()))
                .concat("_")
                .concat(Integer.toString(id))
                .concat(".jpg"));
        file.delete();
        imgRepository.deleteById(id);
        saveData();
    }
}
