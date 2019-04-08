package com.yorix.carcalculator.servise;

import com.yorix.carcalculator.model.Car;
import com.yorix.carcalculator.model.Note;
import com.yorix.carcalculator.storage.NoteRepository;
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
        noteRepository.save(note);
        return note;
    }

    public Note read(Car car, int id) {
        return noteRepository.getByCarAndId(car, id);
    }

    public List<Note> readAllByCarAndDate(Car car, String date) {
        return noteRepository.getAllByCarAndDate(car, date);
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
