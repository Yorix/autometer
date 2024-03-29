package com.yorix.autometer.controller;

import com.yorix.autometer.model.Spare;
import com.yorix.autometer.service.SpareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/spare")
public class SpareController {
    private final SpareService spareService;

    @Autowired
    public SpareController(SpareService spareService) {
        this.spareService = spareService;
    }

    @GetMapping
    public ModelAndView getAll() {
        ModelAndView modelAndView = new ModelAndView("spares");
        List<Spare> spares = spareService.getAll();
        double sum = spares.stream().mapToDouble(spare -> spare.getBuy() + spare.getSale()).sum();
        modelAndView.addObject("budget", spareService.getBudget());
        modelAndView.addObject("balance", spareService.getBalance());
        modelAndView.addObject("sum", sum);
        modelAndView.addObject("spares", spares);
        modelAndView.addObject("spare", new Spare());
        return modelAndView;
    }

    @PostMapping
    public String create(Spare spare) {
        spareService.create(spare);
        return "redirect:/spare";
    }

    @PutMapping("/{spareId}")
    public String update(@PathVariable("spareId") int spareId, Spare spare) {
        spareService.update(spareId, spare);
        return "redirect:/spare";
    }

    @DeleteMapping("/{spareId}")
    public String delete(@PathVariable("spareId") int spareId) {
        spareService.delete(spareId);
        return "redirect:/spare";
    }
}
