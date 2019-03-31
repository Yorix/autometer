package com.yorix.carcalculator.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "car")
@Data
@ToString(exclude = "notes")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "make")
    @NotNull
    private String make;

    @Column(name = "model")
    @NotNull
    private String model;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "car")
    private List<Note> notes;

    @Column(name = "img_filename")
    private String imgFilename;
}
