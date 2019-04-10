package com.yorix.carcalculator.controller;

import com.yorix.carcalculator.model.Car;
import com.yorix.carcalculator.model.Img;
import com.yorix.carcalculator.service.CarService;
import com.yorix.carcalculator.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public ImgController(ImgService imgService, CarService carService) {
        this.imgService = imgService;
        this.carService = carService;
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

        ModelAndView modelAndView = new ModelAndView("images");
        modelAndView.addObject("imgs", imgs);
        return modelAndView;
    }
}
