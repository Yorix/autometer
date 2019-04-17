package com.yorix.autometer.controller;

import com.yorix.autometer.model.Car;
import com.yorix.autometer.model.Note;
import com.yorix.autometer.service.CarService;
import com.yorix.autometer.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/cars/")
public class NoteController {
    private final NoteService noteService;
    private final CarService carService;

    @Autowired
    public NoteController(NoteService noteService, CarService carService) {
        this.noteService = noteService;
        this.carService = carService;
    }

    @PostMapping("{id}/notes/")
    public String create(@PathVariable int id, Note note) {
        Car car = carService.read(id);
        note.setCar(car);
        noteService.create(note);
        return String.format("redirect:/cars/%s/", id);
    }

    @GetMapping("{id}/notes/")
    public ModelAndView getByCar(@PathVariable("id") int id) {
        Car car = carService.read(id);
        ModelAndView modelAndView = new ModelAndView("notes");
        modelAndView.addObject("car", car);
        modelAndView.addObject("notes", noteService.readAllByCar(car));
        modelAndView.addObject("spending", noteService.getSpendingByCar(car));
        modelAndView.addObject("income", noteService.getIncomeByCar(car));
        return modelAndView;
    }

    @DeleteMapping("{carId}/notes/{noteId}")
    public String delete(@PathVariable("carId") int carId, @PathVariable("noteId") int noteId) {
        noteService.deleteById(noteId);
        return String.format("redirect:/cars/%s/notes/", carId);
    }
}
