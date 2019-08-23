package com.yorix.autometer.controller;

import com.yorix.autometer.model.User;
import com.yorix.autometer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user/")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView userList() {
        ModelAndView modelAndView = new ModelAndView("user-list");
        modelAndView.addObject("users", userService.readAll());
        modelAndView.addObject("budget", userService.getBudget());
        modelAndView.addObject("balance", userService.getBalance());
        return modelAndView;
    }

    @PostMapping
    public String create(@RequestParam User user) {
        userService.create(user);
        return "redirect:/";
    }
}
