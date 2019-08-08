package com.yorix.autometer.controller;

import com.yorix.autometer.model.Car;
import com.yorix.autometer.model.Img;
import com.yorix.autometer.service.CarService;
import com.yorix.autometer.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/cars/")
public class ImgController {
    private final ImgService imgService;
    private final CarService carService;
    private final MultipartProperties multipartProperties;

    @Autowired
    public ImgController(ImgService imgService, CarService carService, MultipartProperties multipartProperties) {
        this.imgService = imgService;
        this.carService = carService;
        this.multipartProperties = multipartProperties;
    }

    @GetMapping("*/img/{filename}/")
    public Img getByFilename(@PathVariable("filename") String filename) {
        return imgService.read(filename);
    }

    @GetMapping("{carId}/img/")
    public ModelAndView getAllByCar(@PathVariable("carId") int carId) {
        Car car = carService.read(carId);
        List<Img> imgs = imgService.readAllByCar(car);
        long maxFileSize = multipartProperties.getMaxFileSize().toBytes();
        ModelAndView modelAndView = new ModelAndView("images");
        modelAndView.addObject("budget", imgService.getBudget());
        modelAndView.addObject("balance", imgService.getBalance());
        modelAndView.addObject("car", car);
        modelAndView.addObject("imgs", imgs);
        modelAndView.addObject("maxFileSize", maxFileSize);
        return modelAndView;
    }

    @PostMapping("{carId}/img/")
    public String create(@RequestParam("file") MultipartFile file, @PathVariable("carId") int carId) {
        Car car = carService.read(carId);
        imgService.create(file, car);
        return String.format("redirect:/cars/%s/img/", carId);
    }

    @DeleteMapping("{carId}/img/")
    public String delete(@PathVariable("carId") int carId, String filename) {
        Car car = carService.read(carId);
        imgService.delete(filename, car);
        return String.format("redirect:/cars/%s/img/", carId);
    }
}
