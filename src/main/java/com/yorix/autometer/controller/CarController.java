package com.yorix.autometer.controller;

import com.yorix.autometer.model.Car;
import com.yorix.autometer.model.CarViewDTO;
import com.yorix.autometer.model.Note;
import com.yorix.autometer.model.User;
import com.yorix.autometer.service.CarService;
import com.yorix.autometer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cars/")
public class CarController {
    private final CarService carService;
    private final UserService userService;

    @Autowired
    public CarController(CarService carService, UserService userService) {
        this.carService = carService;
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView getAll() {
        ModelAndView modelAndView = new ModelAndView("car-list");
        List<CarViewDTO> cars = carService.readAll()
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
        List<CarViewDTO> cars = carService.readAllExcept()
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
        ModelAndView modelAndView = new ModelAndView("car");
        CarViewDTO car = new CarViewDTO(carService.read(id));
        Set<String> roles = car.getUser().getRoles().stream().map(Enum::name).collect(Collectors.toSet());
        List<User> users = userService.readAll();
        modelAndView.addObject("roles", roles);
        modelAndView.addObject("budget", carService.getBudget());
        modelAndView.addObject("balance", carService.getBalance());
        modelAndView.addObject("car", car);
        modelAndView.addObject("note", new Note());
        modelAndView.addObject("users", users);
        return modelAndView;
    }

    @GetMapping("new-car/")
    public ModelAndView newCarPage() {
        ModelAndView modelAndView = new ModelAndView("new-car");
        Car car = new Car();
        List<User> users = userService.readAll();
        modelAndView.addObject("budget", carService.getBudget());
        modelAndView.addObject("balance", carService.getBalance());
        modelAndView.addObject("car", car);
        modelAndView.addObject("users", users);
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
            @RequestParam User user,
            Car car
    ) {
        car.setUser(user);
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
