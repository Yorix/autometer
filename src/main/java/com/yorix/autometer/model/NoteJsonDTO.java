package com.yorix.autometer.model;

import lombok.Getter;

@Getter
public class NoteJsonDTO {
    private int id;
    private String description;
    private double value;
    private String date;
    private int carId;

    public NoteJsonDTO(Note note) {
        this.id = note.getId();
        this.description = note.getDescription();
        this.value = note.getValue();
        this.date = note.getDate();
        this.carId = note.getCar().getId();
    }
}
