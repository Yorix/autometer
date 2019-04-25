package com.yorix.autometer.controller;

import com.yorix.autometer.config.Start;
import com.yorix.autometer.model.Note;
import com.yorix.autometer.model.Visit;
import com.yorix.autometer.service.NoteService;
import com.yorix.autometer.service.ParamService;
import com.yorix.autometer.storage.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/")
public class IndexController {
    private final VisitRepository visitRepository;
    private final ParamService paramService;
    private final NoteService noteService;
    private final Start start;

    @Autowired
    public IndexController(VisitRepository visitRepository,
                           ParamService paramService,
                           NoteService noteService,
                           Start start) {
        this.visitRepository = visitRepository;
        this.paramService = paramService;
        this.noteService = noteService;
        this.start = start;
    }

    @GetMapping
    public String index() {
        Visit visit = new Visit();
        visit.setDescription(String.format("Visited at %s", LocalDateTime.now()));
        visitRepository.save(visit);
        return "redirect:/cars/";
    }

    @GetMapping("header-menu/")
    public ModelAndView budgetFrame() {
        double budget = paramService.read("budget");
        double balance = noteService.readAll()
                .stream()
                .mapToDouble(Note::getValue)
                .sum();
        ModelAndView modelAndView = new ModelAndView("frame-header-menu");
        modelAndView.addObject("budget", budget);
        modelAndView.addObject("balance", balance);
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
}
