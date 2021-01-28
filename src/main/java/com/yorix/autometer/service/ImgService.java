package com.yorix.autometer.service;

import com.yorix.autometer.errors.StorageException;
import com.yorix.autometer.model.Car;
import com.yorix.autometer.model.Img;
import com.yorix.autometer.model.Lot;
import com.yorix.autometer.storage.CarRepository;
import com.yorix.autometer.storage.ImgRepository;
import com.yorix.autometer.storage.AuctionRepository;
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

    public void create(MultipartFile file, Car car, String album) {
        if (StringUtils.isEmpty(file.getOriginalFilename()))
            throw new StorageException("Файл не выбран.");

        Img img = saveImg(car, album);
        String filepath = getFilepath(car, album, img);

        try {
            File dir = new File(filepath).getParentFile();
            dir.mkdirs();
            file.transferTo(new File(filepath));
        } catch (IOException e) {
            throw new StorageException("Ошибка загрузки файла " + file.getOriginalFilename(), e);
        }

        saveData();
    }

    public void create(MultipartFile file, Lot lot) {
        if (StringUtils.isEmpty(file.getOriginalFilename()))
            throw new StorageException("Файл не выбран.");

        Img img = saveImg(lot);
        String filepath = getFilepath(lot, img);

        try {
            file.transferTo(new File(filepath));
        } catch (IOException e) {
            throw new StorageException("Ошибка загрузки файла " + file.getOriginalFilename(), e);
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

    private String getFilepath(Car car, String album, Img img) {
        return getAppProperties().getImageStorageLocation()
                .concat(File.separator)
                .concat("car")
                .concat(File.separator)
                .concat(Integer.toString(car.getId()))
                .concat(File.separator)
                .concat(album)
                .concat(File.separator)
                .concat(Integer.toString(img.getId()))
                .concat(".jpg");
    }

    private String getFilepath(Lot lot, Img img) {
        return getAppProperties().getImageStorageLocation()
                .concat(File.separator)
                .concat("lot")
                .concat(File.separator)
                .concat(Integer.toString(lot.getId()))
                .concat(File.separator)
                .concat(Integer.toString(img.getId()))
                .concat(".jpg");
    }

    public Img read(int id) {
        return imgRepository.getOne(id);
    }

    public List<Img> readAllByCar(Car car) {
        return imgRepository.readAllByCar(car);
    }

    public void delete(Img img, Car car) {
        Pattern pattern = Pattern.compile("(?<=\\/)(\\d+)(?=\\.)");
        Matcher matcher = pattern.matcher(car.getCurrentImg());

        if (matcher.find() && img.getId() == Integer.parseInt(matcher.group())) {
            car.setCurrentImg(getAppProperties().getDefaultImageFilename());
            carRepository.save(car);
        }

        File file = new File(getAppProperties().getImageStorageLocation()
                .concat(File.separator)
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
        if (id == Integer.parseInt(lot.getCurrentImg().replaceAll("^lot\\d+_|.jpg$|.png$", ""))) {
            lot.setCurrentImg(getAppProperties().getDefaultImageFilename());
            auctionRepository.save(lot);
        }

        File file = new File(getAppProperties().getImageStorageLocation()
                .concat(File.separator)
                .concat("lot")
                .concat(Integer.toString(lot.getId()))
                .concat("_")
                .concat(Integer.toString(id))
                .concat(".jpg"));
        file.delete();
        imgRepository.deleteById(id);
        saveData();
    }
}
