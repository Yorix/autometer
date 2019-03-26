package com.yorix.carcalculator.storage;

import com.yorix.carcalculator.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface NoteRepository extends JpaRepository<Note, Integer> {
    Note getByDate(LocalDate date);
    void deleteByDate(LocalDate date);
}
