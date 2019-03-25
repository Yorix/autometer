package com.yorix.carcalculator.servise;

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

    public Note read(int id) {
        return noteRepository.getOne(id);
    }

    public List<Note> readAll() {
        return noteRepository.findAll();
    }

    public Note update(int id, Note car) {
        if (car.getId() == id)
            noteRepository.save(car);
        else car = null;
        return car;
    }

    public void delete(Note car) {
        noteRepository.delete(car);
    }

    public void deleteById(int id) {
        noteRepository.deleteById(id);
    }
}
