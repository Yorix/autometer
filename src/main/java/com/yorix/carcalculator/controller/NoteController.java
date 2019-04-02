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

    @PostMapping("/{id}")
    public String create(@PathVariable int id, Note note) {
        Car car = carService.read(id);
        note.setCar(car);
        noteService.create(note);
        return String.format("redirect:/cars/%s/", id);
    }

    @GetMapping("/{id}/{date}")
    public ModelAndView getByCarAndDate(@PathVariable("id") int id, @PathVariable("date") String date) {
        Car car = carService.read(id);
        ModelAndView modelAndView = new ModelAndView("notes");
        modelAndView.addObject("car", car);
        modelAndView.addObject("notes", noteService.readAllByCarAndDate(car, date));
        modelAndView.addObject("sum", noteService.getSumByCarAndDate(car, date));
        return modelAndView;
    }

    @GetMapping("/{id}/allNotes")
    public ModelAndView getByCar(@PathVariable("id") int id) {
        Car car = carService.read(id);
        ModelAndView modelAndView = new ModelAndView("notes");
        modelAndView.addObject("car", car);
        modelAndView.addObject("notes", noteService.readAllByCar(car));
        modelAndView.addObject("sum", noteService.getSumByCar(car));
        return modelAndView;
    }
}
