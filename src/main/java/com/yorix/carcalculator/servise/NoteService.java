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

    public float getSumByCar(Car car) {
        List<Note> notes = readAllByCar(car);
        return getSum(notes);
    }

    public float getSumByCarAndDate(Car car, String date) {
        List<Note> notes = readAllByCarAndDate(car, date);
        return getSum(notes);
    }

    private float getSum(List<Note> notes) {
        double sum = 0;
        for (Note note : notes)
            sum += note.getValue();
        return (float) (int) (sum * 100) / 100;
    }

//    public Note update(LocalDate date, Note note) {
//        if (note.getDate() == date)
//            noteRepository.save(note);
//        else note = null;
//        return note;
//    }

    public void delete(Note note) {
        noteRepository.delete(note);
    }

}
