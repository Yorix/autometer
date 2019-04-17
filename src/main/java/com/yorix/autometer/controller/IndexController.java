package com.yorix.autometer.controller;

import com.yorix.autometer.model.Visit;
import com.yorix.autometer.service.NoteService;
import com.yorix.autometer.service.ParamService;
import com.yorix.autometer.storage.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/")
public class IndexController {
    private final VisitRepository visitRepository;
    private final ParamService paramService;
    private final NoteService noteService;

    @Autowired
    public IndexController(VisitRepository visitRepository,
                           ParamService paramService,
                           NoteService noteService) {
        this.visitRepository = visitRepository;
        this.paramService = paramService;
        this.noteService = noteService;
    }

    @GetMapping
    public String index() {
        Visit visit = new Visit();
        visit.setDescription(String.format("Visited at %s", LocalDateTime.now()));
        visitRepository.save(visit);
        return "redirect:/cars/";
    }

    @GetMapping("budget/")
    public ModelAndView budgetFrame() {
        int budget = paramService.read("budget");
        float balance = noteService.getBalance();
        ModelAndView modelAndView = new ModelAndView("budget");
        modelAndView.addObject("budget", budget);
        modelAndView.addObject("balance", balance);
        return modelAndView;
    }

    @GetMapping("updates/")
    public String checkUpdates() {
        try {
            String filepath = new File("update.cmd").getAbsolutePath();
            Runtime.getRuntime().exec("cmd /c \"" + filepath + "\"");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/cars/";
    }
}
