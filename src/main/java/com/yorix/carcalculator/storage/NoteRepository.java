package com.yorix.carcalculator.storage;

import com.yorix.carcalculator.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Integer> {
}
