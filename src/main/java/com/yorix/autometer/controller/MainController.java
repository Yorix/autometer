package com.yorix.autometer.controller;

import com.yorix.autometer.config.AppProperties;
import com.yorix.autometer.service.CurrencyParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/")
public class MainController {
    private final CurrencyParser currencyParser;
    private AppProperties properties;

    @Autowired
    public MainController(
            CurrencyParser currencyParser,
            AppProperties properties
    ) {
        this.currencyParser = currencyParser;
        this.properties = properties;
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
}
