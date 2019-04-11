package com.yorix.autometer.service;

import com.yorix.autometer.config.StorageProperties;
import com.yorix.autometer.model.Car;
import com.yorix.autometer.storage.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    private final CarRepository carRepository;
    private final StorageProperties properties;

    @Autowired
    public CarService(CarRepository carRepository, StorageProperties properties) {
        this.carRepository = carRepository;
        this.properties = properties;
    }

    public Car create(Car car) {
        if (car.getImgFilename() == null)
            car.setImgFilename(properties.getDefaultImageFilename());
        return carRepository.save(car);
    }

    public Car read(int id) {
        return carRepository.getOne(id);
    }

    public List<Car> readAll() {
        return carRepository.findAll();
    }

//    public Car update(int id, Car car) {
//        if (car.getId() == id)
//            carRepository.save(car);
//        else car = null;
//        return car;
//    }

    public void deleteById(int id) {
        carRepository.deleteById(id);
    }
}