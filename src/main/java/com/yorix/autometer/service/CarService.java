package com.yorix.autometer.service;

import com.yorix.autometer.model.Car;
import com.yorix.autometer.model.Img;
import com.yorix.autometer.model.Note;
import com.yorix.autometer.storage.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CarService extends AppService {
    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public void create(Car car) {
        if (car.getImgFilename() == null)
            car.setImgFilename(getProperties().getDefaultImageFilename());
        car.setMake(car.getMake().replace(Character.toString(160), " ").trim());
        car.setModel(car.getModel().replace(Character.toString(160), " ").trim());
        carRepository.save(car);
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

        if (!StringUtils.isEmpty(newMake))
            car.setMake(newMake.replace(Character.toString(160), " ").trim());
        if (!StringUtils.isEmpty(newModel))
            car.setModel(newModel.replace(Character.toString(160), " ").trim());
        if (newImgFilename != null)
            car.setImgFilename(newImgFilename);
        if (newNotes != null)
            car.setNotes(newNotes);
        if (newImgs != null)
            car.setImgs(newImgs);

        carRepository.save(car);
    }
}
