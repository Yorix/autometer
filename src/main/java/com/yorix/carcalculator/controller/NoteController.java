package com.yorix.carcalculator.controller;

import com.yorix.carcalculator.servise.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.websocket.server.PathParam;

@Controller
@RequestMapping("/notes")
public class NoteController {
    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public ModelAndView get(@PathParam("date") String date) {
        ModelAndView modelAndView = new ModelAndView("note");
        modelAndView.addObject("date", date);
        return modelAndView;
    }
}
