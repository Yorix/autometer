package com.yorix.carcalculator.controller;

import com.yorix.carcalculator.model.Car;
import com.yorix.carcalculator.servise.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping
    public ModelAndView create(Car car) {
        ModelAndView modelAndView = new ModelAndView("car");
        carService.create(car);
        modelAndView.addObject("car", car);
        return modelAndView;
    }

    @GetMapping("/{id}")
    public Car get(@PathVariable("id") int id) {
        return carService.read(id);
    }

    @GetMapping
    public ModelAndView getAll() {
        ModelAndView modelAndView = new ModelAndView("index");
        List<Car> cars = carService.readAll();
        modelAndView.addObject("cars", cars);
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
}
