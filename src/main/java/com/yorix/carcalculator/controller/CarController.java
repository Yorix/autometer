package com.yorix.carcalculator.controller;

import com.yorix.carcalculator.config.CardTextProperties;
import com.yorix.carcalculator.model.Car;
import com.yorix.carcalculator.servise.CarService;
import com.yorix.carcalculator.servise.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;
    private final ImageStorageService imageStorageService;
    private final CardTextProperties properties;

    @Autowired
    public CarController(CarService carService,
                         ImageStorageService imageStorageService,
                         CardTextProperties properties) {
        this.carService = carService;
        this.imageStorageService = imageStorageService;
        this.properties = properties;
    }

    @PostMapping
    public String create(Car car, @RequestParam("file") MultipartFile file) {
        car.setImgFilename(file.getOriginalFilename());
        carService.create(car);
        imageStorageService.store(file);
        return "redirect:/cars" + car.getId();
    }

    @GetMapping("/{id}")
    public ModelAndView get(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView("car");
        Car car = carService.read(id);
        modelAndView.addObject("car", car);
        return modelAndView;
    }

    @GetMapping
    public ModelAndView getAll() {
        ModelAndView modelAndView = new ModelAndView("index");
        List<Car> cars = carService.readAll();
        modelAndView.addObject("cars", cars);
        modelAndView.addObject("textProperties", properties);
        return modelAndView;
    }

    @GetMapping("/newCar")
    public ModelAndView newCarPage() {
        ModelAndView modelAndView = new ModelAndView("newCar");
        modelAndView.addObject("car", new Car());
        return modelAndView;
    }

    @PutMapping("/{id}")
    public Car update(@PathVariable("id") int id, Car car) {
        return carService.update(id, car);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        carService.deleteById(id);
    }

    @DeleteMapping
    public void delete(Car car) {
        carService.delete(car);
    }

    @ExceptionHandler
    public ModelAndView errorHandler(Exception e) {
        ModelAndView modelAndView = new ModelAndView("errorPage");
        modelAndView.addObject("msg", e.getMessage());
        return modelAndView;
    }
}
