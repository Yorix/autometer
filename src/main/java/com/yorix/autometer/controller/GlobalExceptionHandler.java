package com.yorix.autometer.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler implements ErrorController {
    @ExceptionHandler
    public ModelAndView errorPage(Exception ex) {
        String msg = ex.getMessage();
        ModelAndView modelAndView = new ModelAndView("error-page");
        modelAndView.addObject("msg", msg);
        return modelAndView;
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
