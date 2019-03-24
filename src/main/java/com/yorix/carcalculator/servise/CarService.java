package com.yorix.carcalculator.servise;

import com.yorix.carcalculator.model.Car;
import com.yorix.carcalculator.storage.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class CarService {
    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car create(Car car) {
        carRepository.save(car);
        return car;
    }

    public Car read(int id) {
        return carRepository.getOne(id);
    }

    public List<Car> readAll() {
        return carRepository.findAll();
    }

    public Car update(int id, Car car) {
        if (car.getId() == id)
            carRepository.save(car);
        else car = null;
        return car;
    }

    public void delete(Car car) {
        carRepository.delete(car);
    }

    public void deleteById(int id) {
        carRepository.deleteById(id);
    }
}
