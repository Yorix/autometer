package com.yorix.autometer.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ModelAndView errorPage(Exception ex) {
        ModelAndView modelAndView = new ModelAndView("error-page");
        modelAndView.addObject("msg", ex.getMessage());
        return modelAndView;
    }
}
