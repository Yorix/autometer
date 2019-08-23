package com.yorix.autometer.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "note")
@Data
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "description")
    private String description;

    @Column(name = "value", nullable = false)
    private double value;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;
}
