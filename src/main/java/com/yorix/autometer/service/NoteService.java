package com.yorix.autometer.service;

import com.yorix.autometer.model.Car;
import com.yorix.autometer.model.Note;
import com.yorix.autometer.storage.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;

@Service
public class NoteService extends AppService {
    private final NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public void create(Note note) {
        noteRepository.save(note);
        saveData();
    }

    public void update(int id, Note newNote) {
        Note noteFromDb = noteRepository.getOne(id);
        LocalDate newNoteDate = newNote.getDate();
        String newNoteDescription = newNote.getDescription();
        double newNoteValue = newNote.getValue();

        if (!StringUtils.isEmpty(newNoteDescription))
            noteFromDb.setDescription(newNoteDescription);
        noteFromDb.setValue(newNoteValue);
        noteFromDb.setDate(newNoteDate);

        noteRepository.save(noteFromDb);
        saveData();
    }

    public List<Note> readAllByCar(Car car) {
        return noteRepository.getAllByCarOrderByDate(car);
    }

    public List<Note> readAll() {
        return noteRepository.findAll();
    }

    public void deleteById(int id) {
        noteRepository.deleteById(id);
        saveData();
    }
}
