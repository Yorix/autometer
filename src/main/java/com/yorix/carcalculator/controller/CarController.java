package com.yorix.carcalculator.controller;

import com.yorix.carcalculator.config.CardTextProperties;
import com.yorix.carcalculator.model.Car;
import com.yorix.carcalculator.model.Note;
import com.yorix.carcalculator.service.CarService;
import com.yorix.carcalculator.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/cars/")
public class CarController {
    private final CarService carService;
    private final NoteService noteService;
    private final CardTextProperties properties;

    @Autowired
    public CarController(CarService carService,
                         NoteService noteService,
                         CardTextProperties properties) {
        this.carService = carService;
        this.noteService = noteService;
        this.properties = properties;
    }

    @PostMapping
    public String create(Car car) {
        carService.create(car);
        return String.format("redirect:/cars/%s/", car.getId());
    }

    @GetMapping("{id}/")
    public ModelAndView get(@PathVariable("id") int id) {
        Car car = carService.read(id);
        ModelAndView modelAndView = new ModelAndView("car");
        modelAndView.addObject("car", car);
        modelAndView.addObject("spending", noteService.getSpendingByCar(car));
        modelAndView.addObject("income", noteService.getIncomeByCar(car));
        modelAndView.addObject("note", new Note());
        return modelAndView;
    }

    @GetMapping
    public ModelAndView getAll() {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("cars", carService.readAll());
        modelAndView.addObject("textProperties", properties);
        return modelAndView;
    }

    @GetMapping("newCar/")
    public ModelAndView newCarPage() {
        ModelAndView modelAndView = new ModelAndView("newCar");
        modelAndView.addObject("car", new Car());
        return modelAndView;
    }

    @DeleteMapping("{id}/")
    public void delete(@PathVariable("id") int id) {
        carService.deleteById(id);
    }

    @ExceptionHandler
    public ModelAndView errorHandler(Exception e) {
        ModelAndView modelAndView = new ModelAndView("errorPage");
        modelAndView.addObject("msg", e.getMessage());
        return modelAndView;
    }
}
