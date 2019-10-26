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
        return carRepository.findAllByOrderByIdDesc();
    }

    public void create(Car car) {
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
        String newCarMake = newCar.getMake();
        String newCarModel = newCar.getModel();
        int newCarYear = newCar.getYear();
        String newCarCurrentImg = newCar.getCurrentImg();
        List<Note> newCarNotes = newCar.getNotes();
        List<Img> newCarImgs = newCar.getImgs();

        if (!StringUtils.isEmpty(newCarMake))
            carFromDb.setMake(newCarMake.replace(Character.toString(160), " ").trim());
        if (!StringUtils.isEmpty(newCarModel))
            carFromDb.setModel(newCarModel.replace(Character.toString(160), " ").trim());
        if (newCarYear != 0)
            carFromDb.setYear(newCarYear);
        if (!StringUtils.isEmpty(newCarCurrentImg))
            carFromDb.setCurrentImg(newCarCurrentImg);
        if (newCarNotes != null)
            carFromDb.setNotes(newCarNotes);
        if (newCarImgs != null)
            carFromDb.setImgs(newCarImgs);

        carRepository.save(carFromDb);
    }
}
