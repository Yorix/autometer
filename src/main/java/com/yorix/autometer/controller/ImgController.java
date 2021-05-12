package com.yorix.autometer.controller;

import com.yorix.autometer.model.Car;
import com.yorix.autometer.model.Img;
import com.yorix.autometer.model.Lot;
import com.yorix.autometer.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImgController {
    private final ImgService imgService;

    @Autowired
    public ImgController(ImgService imgService) {
        this.imgService = imgService;
    }

    @GetMapping("*/img/{id}")
    public Img getByFilename(@PathVariable int id) {
        return imgService.read(id);
    }

    @PostMapping("/car/{car}/{album}/img")
    public String create(
            @RequestParam("file") MultipartFile file,
            @PathVariable Car car,
            @PathVariable String album
    ) {
        imgService.create(car, album, file);
        return "redirect:/car/" + car.getId();
    }

    @PostMapping("/auc/{lotId}/img")
    public String createLotImg(@RequestParam("file") MultipartFile file, @PathVariable("lotId") Lot lot) {
        imgService.create(lot, file);
        return "redirect:/auc/" + lot.getId();
    }

    @DeleteMapping("/car/{car}/img")
    public String delete(@PathVariable Car car, @RequestParam Img img) {
        imgService.delete(img, car);
        return "redirect:/car/" + car.getId();
    }

    @DeleteMapping("/auc/{lot}/img")
    public String deleteAucImg(@PathVariable Lot lot, @RequestParam int id) {
        imgService.delete(id, lot);
        return "redirect:/auc/" + lot.getId();
    }
}
