package com.yorix.autometer.controller;

import com.yorix.autometer.model.Car;
import com.yorix.autometer.model.CarViewDTO;
import com.yorix.autometer.model.Note;
import com.yorix.autometer.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cars/")
public class CarController {
    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public ModelAndView getAll() {
        ModelAndView modelAndView = new ModelAndView("car-list");
        List<CarViewDTO> cars = carService.readAll(false)
                .stream()
                .map(CarViewDTO::new)
                .collect(Collectors.toList());
        modelAndView.addObject("budget", carService.getBudget());
        modelAndView.addObject("balance", carService.getBalance());
        modelAndView.addObject("cars", cars);
        return modelAndView;
    }

    @GetMapping("orders/")
    public ModelAndView getAllOrders() {
        ModelAndView modelAndView = new ModelAndView("car-list");
        List<CarViewDTO> cars = carService.readAll(true)
                .stream()
                .map(CarViewDTO::new)
                .collect(Collectors.toList());
        modelAndView.addObject("budget", carService.getBudget());
        modelAndView.addObject("balance", carService.getBalance());
        modelAndView.addObject("cars", cars);
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
        return getNewCarModelAndView(false);
    }

    @GetMapping("orders/new-car/")
    public ModelAndView newOrderPage() {
        return getNewCarModelAndView(true);
    }

    private ModelAndView getNewCarModelAndView(boolean ord) {
        ModelAndView modelAndView = new ModelAndView("new-car");
        Car car = new Car();
        car.setOrd(ord);
        modelAndView.addObject("budget", carService.getBudget());
        modelAndView.addObject("balance", carService.getBalance());
        modelAndView.addObject("car", car);
        return modelAndView;
    }

    @PostMapping
    public String create(Car car) {
        carService.create(car);
        carService.saveData();
        return String.format("redirect:/cars/%s/", car.getId());
    }

    @PostMapping("parse/")
    public String createFromSided(@RequestParam String vinOrLot) throws Exception {
        Car car = new Car();
        carService.parse(vinOrLot, car);
        carService.saveData();
        return String.format("redirect:/cars/%s/", car.getId());
    }

    @PostMapping("{id}/pull-info/")
    public String pullCarInfo(@RequestParam String vinOrLot, @PathVariable("id") Car car) throws Exception {
        carService.parse(vinOrLot, car);
        carService.saveData();
        return String.format("redirect:/cars/%s/", car.getId());
    }

    @PutMapping("{id}/")
    public String update(
            @PathVariable("id") int id,
            Car car
    ) {
        carService.update(id, car);
        carService.saveData();
        return "redirect:";
    }

    @DeleteMapping("{id}/")
    public void delete(@PathVariable("id") int id) {
        carService.delete(id);
        carService.saveData();
    }
}
