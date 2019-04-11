package com.yorix.autometer.storage;

import com.yorix.autometer.model.Car;
import com.yorix.autometer.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Integer> {
    List<Note> getAllByCar(Car car);

    Note getByCarAndId(Car car, int id);
}
