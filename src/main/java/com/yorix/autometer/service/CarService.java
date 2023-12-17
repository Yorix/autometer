package com.yorix.autometer.service;

import com.yorix.autometer.model.Car;
import com.yorix.autometer.model.User;
import com.yorix.autometer.storage.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public Car read(int id) {
        return carRepository.getOne(id);
    }

    public List<Car> readAll() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return carRepository.findAllByUserOrderByIdDesc(user);
    }

    public List<Car> readAllExcept() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return carRepository.findAllByUserNotOrderByIdDesc(user);
    }

    public void create(Car car) {
        car.setMake(car.getMake().replaceAll("\\s+", " "));
        car.setModel(car.getModel().replaceAll("\\s+", " "));
        car.setCurrentImg(getAppProperties().getDefaultCarImageFilename());
        carRepository.save(car);
        saveData();
    }

    public void delete(int id) {
        carRepository.deleteById(id);
        saveData();
    }

    public void update(int id, Car newCar) {
        Car carFromDb = read(id);

        if (newCar.getNotes() != null)
            carFromDb.setNotes(newCar.getNotes());
        if (newCar.getImgs() != null)
            carFromDb.setImgs(newCar.getImgs());
        if (newCar.getDateOfComing() != null)
            carFromDb.setDateOfComing(newCar.getDateOfComing());
        if (StringUtils.hasText(newCar.getContainerNumber()))
            carFromDb.setContainerNumber(newCar.getContainerNumber());
        if (newCar.getUser() != null)
            carFromDb.setUser(newCar.getUser());
        if (newCar.getYear() != 0)
            carFromDb.setYear(newCar.getYear());
        if (newCar.getOdometer() != 0)
            carFromDb.setOdometer(newCar.getOdometer());
        if (newCar.getEngine() != 0)
            carFromDb.setEngine(newCar.getEngine());
        if (newCar.getLot() != 0)
            carFromDb.setLot(newCar.getLot());
        if (StringUtils.hasText(newCar.getMake()))
            carFromDb.setMake(newCar.getMake().replace(Character.toString(160), " ").trim());
        if (StringUtils.hasText(newCar.getModel()))
            carFromDb.setModel(newCar.getModel().replace(Character.toString(160), " ").trim());
        if (StringUtils.hasText(newCar.getCurrentImg()))
            carFromDb.setCurrentImg(newCar.getCurrentImg());
        if (StringUtils.hasText(newCar.getVin()))
            carFromDb.setVin(newCar.getVin());
        if (StringUtils.hasText(newCar.getFuel()))
            carFromDb.setFuel(newCar.getFuel());
        if (StringUtils.hasText(newCar.getDriveLine()))
            carFromDb.setDriveLine(newCar.getDriveLine());
        if (StringUtils.hasText(newCar.getTransmission()))
            carFromDb.setTransmission(newCar.getTransmission());
        if (StringUtils.hasText(newCar.getColor()))
            carFromDb.setColor(newCar.getColor());
        if (StringUtils.hasText(newCar.getLoss()))
            carFromDb.setLoss(newCar.getLoss());
        if (StringUtils.hasText(newCar.getDamage()))
            carFromDb.setDamage(newCar.getDamage());
        if (StringUtils.hasText(newCar.getRunAndDrive()))
            carFromDb.setRunAndDrive(newCar.getRunAndDrive());
        if (StringUtils.hasText(newCar.getStarts()))
            carFromDb.setStarts(newCar.getStarts());
        if (StringUtils.hasText(newCar.getCarKeys()))
            carFromDb.setCarKeys(newCar.getCarKeys());

        carRepository.save(carFromDb);
        saveData();
    }
}
