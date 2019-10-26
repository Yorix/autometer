package com.yorix.autometer.model;

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

    @Column(name = "year", nullable = false)
    private int year;

    private int lot;
    private String vin;
    private int odometer;
    private double engine;
    private String fuel;
    private String driveLine;
    private String transmission;
    private String color;
    private String loss;
    private String damage;
    private String runAndDrive;
    private String starts;
    private String carKeys;

    @Column(name = "current_img", nullable = false)
    private String currentImg;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "car")
    private List<Note> notes;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "car")
    private List<Img> imgs;
}
