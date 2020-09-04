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
    private final CarParser carParser;

    @Autowired
    public CarService(CarRepository carRepository, CarParser carParser) {
        this.carRepository = carRepository;
        this.carParser = carParser;
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
        return carRepository.findAllByUserNotLikeOrderByIdDesc(user);
    }

    public void create(Car car) {
        User user = getCurrentUser();
        car.setUser(user);
        car.setMake(car.getMake().replaceAll("\\s+", " "));
        car.setModel(car.getModel().replaceAll("\\s+", " "));
        car.setCurrentImg(getAppProperties().getDefaultImageFilename());
        carRepository.save(car);
    }

    public void parse(String vinOrLot, Car car) throws Exception {
        carParser.parse(vinOrLot, car);
    }

    public void delete(int id) {
        carRepository.deleteById(id);
    }

    public void update(int id, Car newCar) {
        Car carFromDb = read(id);

        if (!StringUtils.isEmpty(newCar.getMake()))
            carFromDb.setMake(newCar.getMake().replace(Character.toString(160), " ").trim());
        if (!StringUtils.isEmpty(newCar.getModel()))
            carFromDb.setModel(newCar.getModel().replace(Character.toString(160), " ").trim());
        if (newCar.getYear() != 0)
            carFromDb.setYear(newCar.getYear());
        if (!StringUtils.isEmpty(newCar.getCurrentImg()))
            carFromDb.setCurrentImg(newCar.getCurrentImg());
        if (newCar.getNotes() != null)
            carFromDb.setNotes(newCar.getNotes());
        if (newCar.getImgs() != null)
            carFromDb.setImgs(newCar.getImgs());
        if (!StringUtils.isEmpty(newCar.getLot()))
            carFromDb.setLot(newCar.getLot());
        if (!StringUtils.isEmpty(newCar.getVin()))
            carFromDb.setVin(newCar.getVin());
        if (newCar.getOdometer() != 0)
            carFromDb.setOdometer(newCar.getOdometer());
        if (newCar.getEngine() != 0)
            carFromDb.setEngine(newCar.getEngine());
        if (!StringUtils.isEmpty(newCar.getFuel()))
            carFromDb.setFuel(newCar.getFuel());
        if (!StringUtils.isEmpty(newCar.getDriveLine()))
            carFromDb.setDriveLine(newCar.getDriveLine());
        if (!StringUtils.isEmpty(newCar.getTransmission()))
            carFromDb.setTransmission(newCar.getTransmission());
        if (!StringUtils.isEmpty(newCar.getColor()))
            carFromDb.setColor(newCar.getColor());
        if (!StringUtils.isEmpty(newCar.getLoss()))
            carFromDb.setLoss(newCar.getLoss());
        if (!StringUtils.isEmpty(newCar.getDamage()))
            carFromDb.setDamage(newCar.getDamage());
        if (!StringUtils.isEmpty(newCar.getRunAndDrive()))
            carFromDb.setRunAndDrive(newCar.getRunAndDrive());
        if (!StringUtils.isEmpty(newCar.getStarts()))
            carFromDb.setStarts(newCar.getStarts());
        if (!StringUtils.isEmpty(newCar.getCarKeys()))
            carFromDb.setCarKeys(newCar.getCarKeys());

        carRepository.save(carFromDb);
    }
}
