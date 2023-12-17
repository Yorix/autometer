package com.yorix.autometer.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Spare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String description;

    @Column(nullable = false)
    private double buy;

    @Column(nullable = false)
    private double sale;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}
