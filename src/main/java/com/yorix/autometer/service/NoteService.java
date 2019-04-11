package com.yorix.autometer.service;

import com.yorix.autometer.model.Car;
import com.yorix.autometer.model.Note;
import com.yorix.autometer.storage.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private final NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Note create(Note note) {
        return noteRepository.save(note);
    }

    public Note read(Car car, int id) {
        return noteRepository.getByCarAndId(car, id);
    }

    public List<Note> readAllByCar(Car car) {
        return noteRepository.getAllByCar(car);
    }

    public List<Note> readAll() {
        return noteRepository.findAll();
    }

    public void delete(Note note) {
        noteRepository.delete(note);
    }


    public float getSpendingByCar(Car car) {
        List<Note> notes = readAllByCar(car);
        return getSpending(notes);
    }

    public float getIncomeByCar(Car car) {
        List<Note> notes = readAllByCar(car);
        return getIncome(notes);
    }


    private float getSpending(List<Note> notes) {
        double sum = 0;
        for (Note note : notes)
            if (note.getValue() < 0)
                sum += note.getValue();
        return (float) (int) (sum * 100) / 100;
    }

    private float getIncome(List<Note> notes) {
        double sum = 0;
        for (Note note : notes)
            if (note.getValue() > 0)
                sum += note.getValue();
        return (float) (int) (sum * 100) / 100;
    }
}
