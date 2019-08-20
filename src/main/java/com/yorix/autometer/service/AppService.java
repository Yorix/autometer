package com.yorix.autometer.service;

import com.yorix.autometer.config.AppProperties;
import com.yorix.autometer.model.Note;
import com.yorix.autometer.storage.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class AppService {
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private ParamService paramService;
    @Autowired
    private AppProperties properties;

    public double getBalance() {
        return noteRepository.findAll()
                .stream()
                .mapToDouble(Note::getValue)
                .sum();
    }

    public double getBudget() {
        return paramService.read("budget");
    }

    public AppProperties getProperties() {
        return properties;
    }
}
