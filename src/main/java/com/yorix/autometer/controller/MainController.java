package com.yorix.autometer.controller;

import com.yorix.autometer.config.AppProperties;
import com.yorix.autometer.config.Start;
import com.yorix.autometer.service.CurrencyParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/")
public class MainController {
    private final CurrencyParser currencyParser;
    private final Start start;
    private AppProperties properties;

    @Autowired
    public MainController(
            CurrencyParser currencyParser,
            Start start,
            AppProperties properties
    ) {
        this.currencyParser = currencyParser;
        this.start = start;
        this.properties = properties;
    }

    @GetMapping("login-error")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("loginError", true);
        return modelAndView;
    }

    @GetMapping("calculator/")
    public ModelAndView calculator() {
        ModelAndView modelAndView = new ModelAndView("calculator");
        for (String currencyCode : properties.getCurrencyCodes()) {
            double rate = currencyParser.getRate(currencyCode, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            modelAndView.addObject(currencyCode, rate);
        }
        modelAndView.addObject("budget", currencyParser.getBudget());
        modelAndView.addObject("balance", currencyParser.getBalance());
        return modelAndView;
    }

    @GetMapping("db")
    public ModelAndView loadDb() {
        ModelAndView modelAndView = new ModelAndView("load-db");
        modelAndView.addObject("budget", currencyParser.getBudget());
        modelAndView.addObject("balance", currencyParser.getBalance());
        return modelAndView;
    }

    @PostMapping("load-data/")
    public String loadData(@RequestParam("file") MultipartFile file) {
        start.readData(file.getOriginalFilename());
        return "redirect:/";
    }
}
