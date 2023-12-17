package com.yorix.autometer.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@ToString(exclude = {"notes", "imgs"})
@EqualsAndHashCode(exclude = {"notes", "imgs"})
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String make;
    @Column(nullable = false)
    private String model;
    @Column(nullable = false)
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfComing;
    private String containerNumber;
    @Column(nullable = false)
    private String currentImg;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "car")
    private List<Note> notes;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "car")
    private List<Img> imgs;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
