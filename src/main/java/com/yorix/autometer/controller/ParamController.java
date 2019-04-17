package com.yorix.autometer.controller;

import com.yorix.autometer.model.Param;
import com.yorix.autometer.service.ParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/params/")
public class ParamController {
    private final ParamService paramService;

    @Autowired
    public ParamController(ParamService paramService) {
        this.paramService = paramService;
    }

    @PostMapping
    public void create(Param param) {
        paramService.create(param);
    }

    @GetMapping("{name}/")
    public int get(@PathVariable("name") String name) {
        return paramService.read(name);
    }

    @PutMapping("{name}/")
    public void update(@PathVariable("name") String name, Param param) {
        paramService.update(name, param);
    }
}
