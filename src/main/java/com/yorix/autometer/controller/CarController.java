package com.yorix.autometer.controller;

import com.yorix.autometer.config.CardTextProperties;
import com.yorix.autometer.model.Car;
import com.yorix.autometer.model.CarViewDTO;
import com.yorix.autometer.model.Note;
import com.yorix.autometer.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cars/")
public class CarController {
    private final CarService carService;
    private final CardTextProperties properties;

    @Autowired
    public CarController(CarService carService, CardTextProperties properties) {
        this.carService = carService;
        this.properties = properties;
    }

    @GetMapping
    public ModelAndView getAll() {
        ModelAndView modelAndView = new ModelAndView("index");
        List<CarViewDTO> cars = carService.readAll()
                .stream()
                .map(CarViewDTO::new)
                .collect(Collectors.toList());
        modelAndView.addObject("budget", carService.getBudget());
        modelAndView.addObject("balance", carService.getBalance());
        modelAndView.addObject("cars", cars);
        modelAndView.addObject("textProperties", properties);
        return modelAndView;
    }

    @GetMapping("{id}/")
    public ModelAndView get(@PathVariable("id") int id) {
        CarViewDTO car = new CarViewDTO(carService.read(id));
        ModelAndView modelAndView = new ModelAndView("car");
        modelAndView.addObject("budget", carService.getBudget());
        modelAndView.addObject("balance", carService.getBalance());
        modelAndView.addObject("car", car);
        modelAndView.addObject("note", new Note());
        return modelAndView;
    }

    @GetMapping("new-car/")
    public ModelAndView newCarPage() {
        ModelAndView modelAndView = new ModelAndView("new-car");
        modelAndView.addObject("budget", carService.getBudget());
        modelAndView.addObject("balance", carService.getBalance());
        modelAndView.addObject("car", new Car());
        return modelAndView;
    }

    @PostMapping
    public String create(Car car) {
        carService.create(car);
        return String.format("redirect:/cars/%s/", car.getId());
    }

    @PutMapping("{id}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") int id, Car car) {
        carService.update(id, car);
    }

    @DeleteMapping("{id}/")
    public void delete(@PathVariable("id") int id) {
        carService.deleteById(id);
    }
}
