package com.yorix.autometer.controller;

import com.yorix.autometer.model.*;
import com.yorix.autometer.service.CarService;
import com.yorix.autometer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
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

    @GetMapping("{car}/")
    public ModelAndView get(@PathVariable Car car) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(currentUser.getRoles().contains(Role.ADMIN) || currentUser.getRoles().contains(Role.POWER)) && !car.getUser().equals(currentUser))
            throw new RuntimeException();
        ModelAndView modelAndView = new ModelAndView("car");
        CarViewDTO carDTO = new CarViewDTO(car);
        List<User> users = userService.readAll();
        modelAndView.addObject("budget", carService.getBudget());
        modelAndView.addObject("balance", carService.getBalance());
        modelAndView.addObject("car", carDTO);
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
        return String.format("redirect:/cars/%s/", car.getId());
    }

    @PostMapping("parse/")
    public String createFromSided(@RequestParam String vinOrLot) throws Exception {
        Car car = new Car();
        carService.parse(vinOrLot, car);
        return String.format("redirect:/cars/%s/", car.getId());
    }

    @PostMapping("{id}/pull-info/")
    public String pullCarInfo(@RequestParam String vinOrLot, @PathVariable("id") Car car) throws Exception {
        carService.parse(vinOrLot, car);
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
        return "redirect:";
    }

    @DeleteMapping("{id}/")
    public void delete(@PathVariable("id") int id) {
        carService.delete(id);
    }
}
