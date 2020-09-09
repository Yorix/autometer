package com.yorix.autometer.controller;

import com.yorix.autometer.model.Lot;
import com.yorix.autometer.service.LotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/auc")
public class LotController {
    private final LotService lotService;

    @Autowired
    public LotController(LotService auctionService) {
        this.lotService = auctionService;
    }

    @GetMapping
    public ModelAndView getAll() {
        ModelAndView modelAndView = new ModelAndView("auction");
        List<Lot> lots = lotService.getAll();
        modelAndView.addObject("lots", lots);
        modelAndView.addObject("budget", lotService.getBudget());
        modelAndView.addObject("balance", lotService.getBalance());
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView getLot(@PathVariable int id) {
        ModelAndView modelAndView = new ModelAndView("lot");
        Lot lot = lotService.get(id);
        modelAndView.addObject("lot", lot);
        modelAndView.addObject("budget", lotService.getBudget());
        modelAndView.addObject("balance", lotService.getBalance());
        return modelAndView;
    }

    @GetMapping("/new-lot")
    public ModelAndView newLot() {
        ModelAndView modelAndView = new ModelAndView("new-lot");
        modelAndView.addObject("lot", new Lot());
        modelAndView.addObject("budget", lotService.getBudget());
        modelAndView.addObject("balance", lotService.getBalance());
        return modelAndView;
    }

    @PostMapping
    public String newLot(Lot lot) {
        lotService.create(lot);
        return "redirect:/auc/" + lot.getId();
    }
}
