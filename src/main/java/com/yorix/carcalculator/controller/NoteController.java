package com.yorix.carcalculator.controller;

import com.yorix.carcalculator.model.Note;
import com.yorix.carcalculator.servise.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.websocket.server.PathParam;

@Controller
@RequestMapping("/cars")
public class NoteController {
    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    //TODO !!!!!!!!!!!

    @PostMapping("/{id}")
    public String create(@PathVariable String id, Note note) {
        noteService.create(note);
        return "redirect:/cars/" + id + "/notes";
    }

    @GetMapping("/{id}/notes")
    public ModelAndView get(@PathVariable String id, @PathParam("date") String date) {
        ModelAndView modelAndView = new ModelAndView("note");
        modelAndView.addObject("carId", id);
        modelAndView.addObject("date", date);
        return modelAndView;
    }
}
