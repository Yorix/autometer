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

    @PostMapping("{id}/img/")
    public String create(@RequestParam("file") MultipartFile file, @PathVariable("id") int id) {
        Car car = carService.read(id);
        imgService.create(file, car);
        return String.format("redirect:/cars/%s/img/", id);
    }

    @GetMapping("{id}/img/{filename}/")
    public Img getByFilename(@PathVariable("id") int id, @PathVariable("filename") String filename) {
        Car car = carService.read(id);
        return imgService.read(filename);
    }

    @GetMapping("{id}/img/")
    public ModelAndView getAllByCar(@PathVariable("id") int id) {
        Car car = carService.read(id);
        List<Img> imgs = imgService.readAllByCar(car);
        long maxFileSize = multipartProperties.getMaxFileSize().toBytes();
        ModelAndView modelAndView = new ModelAndView("images");
        modelAndView.addObject("car", car);
        modelAndView.addObject("imgs", imgs);
        modelAndView.addObject("maxFileSize", maxFileSize);
        return modelAndView;
    }
}
