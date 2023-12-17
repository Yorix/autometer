package com.yorix.autometer.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalController {
    @ModelAttribute("servletPath")
    String getRequestServletPath(HttpServletRequest request) {
        return request.getServletPath();
    }

    @ExceptionHandler
    public ModelAndView errorPage(Exception ex) {
        String msg = ex.getMessage();
        ex.printStackTrace();
        ModelAndView modelAndView = new ModelAndView("error-page");
        modelAndView.addObject("msg", msg);
        return modelAndView;
    }
}
