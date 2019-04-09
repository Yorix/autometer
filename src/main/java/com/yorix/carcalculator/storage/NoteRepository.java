package com.yorix.carcalculator.storage;

import com.yorix.carcalculator.model.Car;
import com.yorix.carcalculator.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Integer> {
    List<Note> getAllByCar(Car car);

    Note getByCarAndId(Car car, int id);
}
