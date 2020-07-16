package com.yorix.autometer.controller;

import com.yorix.autometer.model.Car;
import com.yorix.autometer.model.CarViewDTO;
import com.yorix.autometer.model.Note;
import com.yorix.autometer.service.SpareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/spares/")
public class SpareController {
    private final SpareService spareService;

    @Autowired
    public SpareController(SpareService spareService) {
        this.spareService = spareService;
    }

    @GetMapping
    public ModelAndView getAll() {
        ModelAndView modelAndView = new ModelAndView("spares");
        List<CarViewDTO> cars = null;
        modelAndView.addObject("budget", 0.0);
        modelAndView.addObject("balance", 0.0);
        modelAndView.addObject("cars", cars);
        return modelAndView;
    }
}
