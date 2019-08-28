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

    public void delete(int id) {
        carRepository.deleteById(id);
    }

    public void update(int id, Car newCar) {
        Car carFromDb = read(id);
        String newCarMake = newCar.getMake();
        String newCarModel = newCar.getModel();
        String newCarImgFilename = newCar.getImgFilename();
        List<Note> newCarNotes = newCar.getNotes();
        List<Img> newCarImgs = newCar.getImgs();

        if (!StringUtils.isEmpty(newCarMake))
            carFromDb.setMake(newCarMake.replace(Character.toString(160), " ").trim());
        if (!StringUtils.isEmpty(newCarModel))
            carFromDb.setModel(newCarModel.replace(Character.toString(160), " ").trim());
        if (newCarImgFilename != null)
            carFromDb.setImgFilename(newCarImgFilename);
        if (newCarNotes != null)
            carFromDb.setNotes(newCarNotes);
        if (newCarImgs != null)
            carFromDb.setImgs(newCarImgs);

        carRepository.save(carFromDb);
    }
}
