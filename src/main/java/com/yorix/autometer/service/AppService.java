package com.yorix.autometer.service;

import com.yorix.autometer.config.AppProperties;
import com.yorix.autometer.model.Note;
import com.yorix.autometer.storage.NoteRepository;
import com.yorix.autometer.storage.ParamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class AppService {
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private ParamRepository paramRepository;
    @Autowired
    private AppProperties properties;

    public double getBalance() {
        return noteRepository.findAll()
                .stream()
                .mapToDouble(Note::getValue)
                .sum();
    }

    public double getBudget() {
        return paramRepository.getOne("budget").getValue();
    }

    public AppProperties getProperties() {
        return properties;
    }
}
