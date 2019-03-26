package com.yorix.carcalculator.servise;

import com.yorix.carcalculator.model.Note;
import com.yorix.carcalculator.storage.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public Note read(LocalDate date) {
        return noteRepository.getByDate(date);
    }

    public List<Note> readAll() {
        return noteRepository.findAll();
    }

    public Note update(LocalDate date, Note note) {
        if (note.getDate() == date)
            noteRepository.save(note);
        else note = null;
        return note;
    }

    public void delete(Note car) {
        noteRepository.delete(car);
    }

    public void deleteByDate(LocalDate date) {
        noteRepository.deleteByDate(date);
    }
}
