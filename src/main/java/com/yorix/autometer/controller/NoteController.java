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

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

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
        ModelAndView modelAndView = new ModelAndView("notes");
        Car car = carService.read(carId);
        Set<String> roles = car.getUser().getRoles().stream().map(Enum::name).collect(Collectors.toSet());
        modelAndView.addObject("roles", roles);
        modelAndView.addObject("budget", noteService.getBudget());
        modelAndView.addObject("balance", noteService.getBalance());
        modelAndView.addObject("car", new CarViewDTO(car));
        modelAndView.addObject("notes", noteService.readAllByCar(car));
        modelAndView.addObject("note", new Note());
        return modelAndView;
    }

    @PostMapping
    public String create(
            @PathVariable("carId") Car car,
            @RequestParam(name = "location") String location,
            @Valid Note note
    ) {
        note.setCar(car);
        noteService.create(note);
        noteService.saveData();
        return "redirect:" + location;
    }

    @PutMapping("{noteId}/")
    public String update(
            @PathVariable("carId") int carId,
            @PathVariable("noteId") int noteId,
            Note note
    ) {
        noteService.update(noteId, note);
        noteService.saveData();
        return String.format("redirect:/cars/%s/notes/", carId);
    }

    @DeleteMapping("{noteId}/")
    public String delete(@PathVariable("carId") int carId, @PathVariable("noteId") int noteId) {
        noteService.deleteById(noteId);
        noteService.saveData();
        return String.format("redirect:/cars/%s/notes/", carId);
    }
}
