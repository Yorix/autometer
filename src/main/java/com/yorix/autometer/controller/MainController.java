package com.yorix.autometer.controller;

import com.yorix.autometer.config.AppProperties;
import com.yorix.autometer.config.Start;
import com.yorix.autometer.model.Visit;
import com.yorix.autometer.service.CurrencyParser;
import com.yorix.autometer.storage.VisitRepository;
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
    private final VisitRepository visitRepository;
    private final Start start;
    private AppProperties properties;

    @Autowired
    public MainController(
            CurrencyParser currencyParser,
            VisitRepository visitRepository,
            Start start,
            AppProperties properties
    ) {
        this.currencyParser = currencyParser;
        this.visitRepository = visitRepository;
        this.start = start;
        this.properties = properties;
    }

    @GetMapping
    public String index() {
        Visit visit = new Visit();
        visit.setDescription(String.format("Visited at %s", LocalDateTime.now()));
        visitRepository.save(visit);
        return "redirect:/cars/";
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

    @GetMapping("updates/")
    @ResponseBody
    public String checkUpdates() {
        return start.getUpdateAns();
    }

    @GetMapping("install/")
    public String installUpdate() {
        start.installUpdate();
        return "redirect:/";
    }

    @PostMapping("load-data/")
    public String loadData(@RequestParam("file") MultipartFile file) {
        start.readData(file.getOriginalFilename());
        return "redirect:/";
    }

    @GetMapping("exit/")
    public void exit() {
        System.exit(0);
    }
}
