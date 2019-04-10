package com.yorix.carcalculator.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "car")
@Data
@ToString(exclude = {"notes", "imgs"})
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "make", nullable = false)
    private String make;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "img_filename", nullable = false)
    private String imgFilename;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "car")
    private List<Note> notes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "car")
    private List<Img> imgs;
}
