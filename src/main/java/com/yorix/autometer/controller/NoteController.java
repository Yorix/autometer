package com.yorix.autometer.controller;

import com.yorix.autometer.model.Car;
import com.yorix.autometer.model.CarViewDTO;
import com.yorix.autometer.model.Note;
import com.yorix.autometer.service.CarService;
import com.yorix.autometer.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/cars/{carId}/notes/")
public class NoteController {
    private final NoteService noteService;
    private final CarService carService;

    @Autowired
    public NoteController(NoteService noteService, CarService carService) {
        this.noteService = noteService;
        this.carService = carService;
    }

    @GetMapping
    public ModelAndView getByCar(@PathVariable("carId") int carId) {
        Car car = carService.read(carId);
        ModelAndView modelAndView = new ModelAndView("notes");
        modelAndView.addObject("budget", noteService.getBudget());
        modelAndView.addObject("balance", noteService.getBalance());
        modelAndView.addObject("car", new CarViewDTO(car));
        modelAndView.addObject("notes", noteService.readAllByCar(car));
        modelAndView.addObject("note", new Note());
        return modelAndView;
    }

    @PostMapping
    public String create(
            @PathVariable("carId") int carId,
            @RequestParam(name = "location") String location,
            @Valid Note note
    ) {
        Car car = carService.read(carId);
        note.setCar(car);
        noteService.create(note);
        return "redirect:" + location;
    }

    @PutMapping("{noteId}/")
    public String update(
            @PathVariable("carId") int carId,
            @PathVariable("noteId") int noteId,
            Note note
    ) {
        noteService.update(noteId, note);
        return String.format("redirect:/cars/%s/notes/", carId);
    }

    @DeleteMapping("{noteId}/")
    public String delete(@PathVariable("carId") int carId, @PathVariable("noteId") int noteId) {
        noteService.deleteById(noteId);
        return String.format("redirect:/cars/%s/notes/", carId);
    }
}
