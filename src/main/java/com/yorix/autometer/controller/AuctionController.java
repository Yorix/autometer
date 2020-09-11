package com.yorix.autometer.controller;

import com.yorix.autometer.model.Lot;
import com.yorix.autometer.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/auc")
public class AuctionController {
    private final AuctionService auctionService;
    private final MultipartProperties multipartProperties;

    @Autowired
    public AuctionController(AuctionService auctionService, MultipartProperties multipartProperties) {
        this.auctionService = auctionService;
        this.multipartProperties = multipartProperties;
    }

    @GetMapping
    public ModelAndView getAll() {
        ModelAndView modelAndView = new ModelAndView("auction");
        List<Lot> lots = auctionService.readAll();
        modelAndView.addObject("lots", lots);
        modelAndView.addObject("budget", auctionService.getBudget());
        modelAndView.addObject("balance", auctionService.getBalance());
        return modelAndView;
    }

    @GetMapping("/{lotFromDb}")
    public ModelAndView getLot(@PathVariable Lot lotFromDb) {
        ModelAndView modelAndView = new ModelAndView("lot");
        long maxFileSize = multipartProperties.getMaxFileSize().toBytes();
        modelAndView.addObject("lot", lotFromDb);
        modelAndView.addObject("budget", auctionService.getBudget());
        modelAndView.addObject("balance", auctionService.getBalance());
        modelAndView.addObject("maxFileSize", maxFileSize);
        return modelAndView;
    }

    @GetMapping("/new-lot")
    public ModelAndView newLot() {
        ModelAndView modelAndView = new ModelAndView("new-lot");
        modelAndView.addObject("lot", new Lot());
        modelAndView.addObject("budget", auctionService.getBudget());
        modelAndView.addObject("balance", auctionService.getBalance());
        return modelAndView;
    }

    @PostMapping
    public String newLot(Lot lot) {
        auctionService.create(lot);
        return "redirect:/auc/" + lot.getId();
    }

    @PutMapping("/{id}")
    public String updateLot(@PathVariable int id, Lot lot) {
        auctionService.update(id, lot);
        return "redirect:/auc/" + id;
    }

    @PutMapping("/{lot}/bid")
    public String bid(@PathVariable Lot lot,
                      @RequestParam int step,
                      @RequestParam String userPhone,
                      HttpServletRequest request) {
        String clientIp = request.getRemoteAddr();
        if (auctionService.checkIp(lot.getId(), clientIp))
            throw new RuntimeException("Вы недавно делали ставку.");
        lot.setUserPhone(userPhone);
        lot.setCurrentBid(lot.getCurrentBid() + step);
        auctionService.update(lot.getId(), lot);
        return "redirect:/auc/" + lot.getId();
    }
}
