package com.yorix.autometer.service;

import com.yorix.autometer.model.Car;
import com.yorix.autometer.model.Note;
import com.yorix.autometer.storage.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class NoteService extends AppService {
    private final NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public void create(Note note) {
        String date = LocalDate.parse(note.getDate()).format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));
        note.setDate(date);
        noteRepository.save(note);
    }

    public void update(int id, Note newNote) {
        Note noteFromDb = noteRepository.getOne(id);
        String newNoteDate = newNote.getDate();
        String newNoteDescription = newNote.getDescription();
        double newNoteValue = newNote.getValue();

        if (!StringUtils.isEmpty(newNoteDate))
            noteFromDb.setDate(newNoteDate);
        if (!StringUtils.isEmpty(newNoteDescription))
            noteFromDb.setDescription(newNoteDescription);
        noteFromDb.setValue(newNoteValue);

        noteRepository.save(noteFromDb);
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

    public void deleteById(int id) {
        noteRepository.deleteById(id);
    }
}
