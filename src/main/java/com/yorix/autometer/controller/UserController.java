package com.yorix.autometer.controller;

import com.yorix.autometer.model.Role;
import com.yorix.autometer.model.User;
import com.yorix.autometer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.Map;

@Controller
@RequestMapping("/user")
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

    @GetMapping("/{user}")
    public ModelAndView get(@PathVariable("user") User user) {
        ModelAndView modelAndView = new ModelAndView("user");
        modelAndView.addObject("user", user);
        modelAndView.addObject("roles", Role.values());
        modelAndView.addObject("budget", userService.getBudget());
        modelAndView.addObject("balance", userService.getBalance());
        return modelAndView;
    }

    @GetMapping("/save-visit")
    public String saveVisit(@AuthenticationPrincipal User user) {
        userService.saveVisit(user);
        return "redirect:/";
    }

    @GetMapping("/get-visits")
    public ModelAndView getVisits(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ) {
        if (from == null) from = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        if (to == null) to = LocalDateTime.now();
        ModelAndView modelAndView = new ModelAndView("visits");
        modelAndView.addObject("visits", userService.getVisits(from, to));
        modelAndView.addObject("from", from.withSecond(0).withNano(0));
        modelAndView.addObject("to", to.withSecond(0).withNano(0));
        modelAndView.addObject("budget", userService.getBudget());
        modelAndView.addObject("balance", userService.getBalance());
        return modelAndView;
    }

    @PostMapping
    public ModelAndView create(@Valid User user, BindingResult bindingResult, @RequestParam("passwordConfirm") String passwordConfirm) {
        ModelAndView modelAndView = new ModelAndView("user-list");
        if (bindingResult.hasErrors()) {
            modelAndView.addAllObjects(ControllerUtils.getErrors(bindingResult));
        }
        if (userService.loadUserByUsername(user.getUsername()) != null) {
            modelAndView.addObject("userExistsError", "Пользователь с таким именем уже существует.");
        }
        if (!passwordConfirm.equals(user.getPassword())) {
            modelAndView.addObject("passConfirmError", "Пароли не совпадают.");
        }
        if (modelAndView.getModel().isEmpty()) {
            userService.create(user);
        }
        modelAndView.addObject("users", userService.readAll());
        modelAndView.addObject("budget", userService.getBudget());
        modelAndView.addObject("balance", userService.getBalance());
        return modelAndView;
    }

    @PutMapping("/{user}")
    public String update(
            @AuthenticationPrincipal User activeUser,
            @PathVariable("user") User user,
            @RequestParam Map<String, String> form,
            @RequestParam(name = "file", required = false) MultipartFile file,
            Model model
    ) {
        String password = form.get("password");
        String passwordConfirm = form.get("passwordConfirm");

        if ((!activeUser.getRoles().contains(Role.ADMIN) && !activeUser.getRoles().contains(Role.POWER) && activeUser.getId() != user.getId())
                || (!activeUser.getRoles().contains(Role.ADMIN) && (user.getRoles().contains(Role.ADMIN) || user.getRoles().contains(Role.POWER)) && activeUser.getId() != user.getId())) {
            model.addAttribute("accessError", "Недостаточно полномочий.");
        }
        if (!passwordConfirm.equals(password)) {
            model.addAttribute("passConfirmError", "Пароли не совпадают.");
        }
        if (model.asMap().size() <= 1) {
            userService.update(user, form, file);
            return "redirect:/user";
        }
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        model.addAttribute("budget", userService.getBudget());
        model.addAttribute("balance", userService.getBalance());
        return "user";
    }


    @DeleteMapping("/{user}")
    public String delete(
            @PathVariable("user") User user,
            @AuthenticationPrincipal User activeUser,
            Model model
    ) {
        if (
                !activeUser.getRoles().contains(Role.ADMIN) && !activeUser.getRoles().contains(Role.POWER)
                || (!activeUser.getRoles().contains(Role.ADMIN) && (user.getRoles().contains(Role.ADMIN) || user.getRoles().contains(Role.POWER)))
        ) {
            model.addAttribute("accessError", "Недостаточно полномочий.");
            model.addAttribute("user", user);
            model.addAttribute("roles", Role.values());
            model.addAttribute("budget", userService.getBudget());
            model.addAttribute("balance", userService.getBalance());
            return "user";
        }
        userService.delete(user);
        return "redirect:../";
    }
}