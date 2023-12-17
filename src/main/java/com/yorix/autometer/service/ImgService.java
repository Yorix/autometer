package com.yorix.autometer.service;

import com.yorix.autometer.errors.StorageException;
import com.yorix.autometer.model.Car;
import com.yorix.autometer.model.Img;
import com.yorix.autometer.model.Lot;
import com.yorix.autometer.storage.AuctionRepository;
import com.yorix.autometer.storage.CarRepository;
import com.yorix.autometer.storage.ImgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ImgService extends AppService {
    private final ImgRepository imgRepository;
    private final CarRepository carRepository;
    private final AuctionRepository auctionRepository;

    @Autowired
    public ImgService(ImgRepository imgRepository, CarRepository carRepository, AuctionRepository auctionRepository) {
        this.imgRepository = imgRepository;
        this.carRepository = carRepository;
        this.auctionRepository = auctionRepository;
    }

    public void create(Car car, String album, MultipartFile[] files) {
        if (files.length < 1)
            throw new StorageException("Файл не выбран.");

        String dirPath = getDirPath(car, album);
        Path.of(dirPath).toFile().mkdirs();

        for (MultipartFile file : files) {
            Img img = saveImg(car, album);
            String filepath = dirPath
                    .concat(Integer.toString(img.getId()))
                    .concat(".jpg");

            try {
                file.transferTo(new File(filepath));
            } catch (IOException e) {
                throw new StorageException("Ошибка загрузки файла " + file.getOriginalFilename(), e);
            }
        }

        saveData();
    }

    public void create(Lot lot, MultipartFile[] files) {
        if (files.length < 1)
            throw new StorageException("Файл не выбран.");

        String dirPath = getDirPath(lot);
        Path.of(dirPath).toFile().mkdirs();

        for (MultipartFile file : files) {
            Img img = saveImg(lot);
            String filepath = dirPath
                    .concat(Integer.toString(img.getId()))
                    .concat(".jpg");

            try {
                file.transferTo(new File(filepath));
            } catch (IOException e) {
                throw new StorageException("Ошибка загрузки файла " + file.getOriginalFilename(), e);
            }
        }

        saveData();
    }

    private Img saveImg(Car car, String album) {
        Img img = new Img();
        img.setCar(car);
        img.setAlbum(album);
        imgRepository.save(img);
        return img;
    }

    private Img saveImg(Lot lot) {
        Img img = new Img();
        img.setLot(lot);
        imgRepository.save(img);
        return img;
    }

    private String getDirPath(Car car, String album) {
        return getAppProperties().getImageStorageLocation()
                .concat("car")
                .concat(File.separator)
                .concat(Integer.toString(car.getId()))
                .concat(File.separator)
                .concat(album)
                .concat(File.separator);
    }

    private String getDirPath(Lot lot) {
        return getAppProperties().getImageStorageLocation()
                .concat("lot")
                .concat(File.separator)
                .concat(Integer.toString(lot.getId()))
                .concat(File.separator);
    }

    public Img read(int id) {
        return imgRepository.getOne(id);
    }

    public List<Img> readAllByCar(Car car) {
        return imgRepository.readAllByCar(car);
    }

    public void delete(Img img, Car car) {
        Pattern pattern = Pattern.compile("(?<=/)(\\d+)(?=\\.)");
        Matcher matcher = pattern.matcher(car.getCurrentImg());

        if (matcher.find() && img.getId() == Integer.parseInt(matcher.group())) {
            car.setCurrentImg(getAppProperties().getDefaultCarImageFilename());
            carRepository.save(car);
        }

        File file = new File(getAppProperties().getImageStorageLocation()
                .concat("car")
                .concat(File.separator)
                .concat(Integer.toString(car.getId()))
                .concat(File.separator)
                .concat(img.getAlbum())
                .concat(File.separator)
                .concat(Integer.toString(img.getId()))
                .concat(".jpg"));
        file.delete();
        imgRepository.delete(img);
        saveData();
    }

    public void delete(int id, Lot lot) {
        Pattern pattern = Pattern.compile("(?<=/)(\\d+)(?=\\.)");
        Matcher matcher = pattern.matcher(lot.getCurrentImg());

        if (matcher.find() && id == Integer.parseInt(matcher.group())) {
            lot.setCurrentImg(getAppProperties().getDefaultCarImageFilename());
            auctionRepository.save(lot);
        }

        File file = new File(getAppProperties().getImageStorageLocation()
                .concat("lot")
                .concat(File.separator)
                .concat(Integer.toString(lot.getId()))
                .concat(File.separator)
                .concat(Integer.toString(id))
                .concat(".jpg"));
        file.delete();
        imgRepository.deleteById(id);
        saveData();
    }
}
