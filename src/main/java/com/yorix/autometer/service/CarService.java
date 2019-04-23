package com.yorix.autometer.service;

import com.yorix.autometer.config.AppProperties;
import com.yorix.autometer.model.Car;
import com.yorix.autometer.model.Img;
import com.yorix.autometer.model.Note;
import com.yorix.autometer.storage.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    private final CarRepository carRepository;
    private final AppProperties properties;

    @Autowired
    public CarService(CarRepository carRepository, AppProperties properties) {
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

    public void deleteById(int id) {
        carRepository.deleteById(id);
    }

    public void update(int id, Car updated) {
        Car car = read(id);
        String newMake = updated.getMake();
        String newModel = updated.getModel();
        String newImgFilename = updated.getImgFilename();
        List<Note> newNotes = updated.getNotes();
        List<Img> newImgs = updated.getImgs();

        if (newMake != null)
            car.setMake(newMake);
        if (newModel != null)
            car.setModel(newModel);
        if (newImgFilename != null)
            car.setImgFilename(newImgFilename);
        if (newNotes != null)
            car.setNotes(newNotes);
        if (newImgs != null)
            car.setImgs(newImgs);

        carRepository.save(car);
    }
}
