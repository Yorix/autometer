package com.yorix.autometer.controller;

import com.yorix.autometer.model.Param;
import com.yorix.autometer.service.ParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/param")
public class ParamController {
    private final ParamService paramService;

    @Autowired
    public ParamController(ParamService paramService) {
        this.paramService = paramService;
    }

    @GetMapping("/{name}")
    @ResponseBody
    public double get(@PathVariable("name") String name) {
        return paramService.read(name);
    }

    @PostMapping
    @ResponseBody
    public void create(Param param) {
        paramService.create(param);
    }

    @PutMapping("/{name}")
    public String update(
            @PathVariable("name") String name,
            @RequestParam(name = "paramValue") double val,
            @RequestParam(name = "location") String location
    ) {
        val += paramService.getBudget();
        Param param = new Param(name, val);
        paramService.update(name, param);
        return "redirect:" + location;
    }
}
