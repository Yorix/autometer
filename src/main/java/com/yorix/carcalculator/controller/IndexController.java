package com.yorix.carcalculator.controller;

import com.yorix.carcalculator.model.Car;
import com.yorix.carcalculator.model.Visit;
import com.yorix.carcalculator.storage.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/")
public class IndexController {
    private final VisitRepository visitRepository;

    @Autowired
    public IndexController(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @GetMapping
    public String index() {
        Visit visit = new Visit();
        visit.setDescription(String.format("Visited at %s", LocalDateTime.now()));
        visitRepository.save(visit);
        return "redirect:/cars";
    }

    @GetMapping("cars/newCar")
    public ModelAndView newCarPage() {
        ModelAndView modelAndView = new ModelAndView("newCar");
        modelAndView.addObject("car", new Car());
        return modelAndView;
    }
}
