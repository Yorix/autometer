package com.yorix.autometer.controller;

import com.yorix.autometer.model.*;
import com.yorix.autometer.service.CarService;
import com.yorix.autometer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/car")
public class CarController {
    private final CarService carService;
    private final UserService userService;
    private final MultipartProperties multipartProperties;

    @Autowired
    public CarController(CarService carService, UserService userService, MultipartProperties multipartProperties) {
        this.carService = carService;
        this.userService = userService;
        this.multipartProperties = multipartProperties;
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

    @GetMapping("/order")
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

    @GetMapping("/{carFromDb}")
    public ModelAndView get(@PathVariable Car carFromDb) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(currentUser.getRoles().contains(Role.ADMIN) || currentUser.getRoles().contains(Role.POWER)) && !carFromDb.getUser().equals(currentUser))
            throw new RuntimeException();
        ModelAndView modelAndView = new ModelAndView("car");
        CarViewDTO carDTO = new CarViewDTO(carFromDb);
        List<User> users = userService.readAll();
        long maxFileSize = multipartProperties.getMaxFileSize().toBytes();
        modelAndView.addObject("budget", carService.getBudget());
        modelAndView.addObject("balance", carService.getBalance());
        modelAndView.addObject("car", carDTO);
        modelAndView.addObject("note", new Note());
        modelAndView.addObject("users", users);
        modelAndView.addObject("maxFileSize", maxFileSize);
        return modelAndView;
    }

    @GetMapping("/new-car")
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
        return "redirect:/car/" + car.getId();
    }

    @PutMapping("/{id}")
    public String update(
            @PathVariable int id,
            @RequestParam User user,
            Car car
    ) {
        car.setUser(user);
        carService.update(id, car);
        return "redirect:/car/" + id;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        carService.delete(id);
    }
}
