package com.yorix.carcalculator.servise;

import com.yorix.carcalculator.model.Car;
import com.yorix.carcalculator.model.Note;
import com.yorix.carcalculator.storage.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public List<Note> readAllByCarAndDate(Car car, LocalDateTime date) {
        return noteRepository.getAllByCarAndDate(car, date);
    }

    public List<Note> readAllByCar(Car car) {
        return noteRepository.getAllByCar(car);
    }

    public List<Note> readAll() {
        return noteRepository.findAll();
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
