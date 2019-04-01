package com.yorix.carcalculator.controller;

import com.yorix.carcalculator.model.Car;
import com.yorix.carcalculator.model.Note;
import com.yorix.carcalculator.servise.CarService;
import com.yorix.carcalculator.servise.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/cars")
public class NoteController {
    private final NoteService noteService;
    private final CarService carService;

    @Autowired
    public NoteController(NoteService noteService, CarService carService) {
        this.noteService = noteService;
        this.carService = carService;
    }

    //TODO !!!!!!!!!!!

    @PostMapping("/{id}")
    public String create(@PathVariable int id, Note note) {
        Car car = carService.read(id);
        note.setCar(car);
        noteService.create(note);
        return String.format("redirect:/cars/%s/", id);
    }

    @GetMapping("/{id}/{date}")
    public ModelAndView get(@PathVariable("id") int id, @PathVariable("date") LocalDateTime date) {
        Car car = carService.read(id);
        ModelAndView modelAndView = new ModelAndView("notes");
        modelAndView.addObject("notes", noteService.readAllByCarAndDate(car, date));
        return modelAndView;
    }
}
