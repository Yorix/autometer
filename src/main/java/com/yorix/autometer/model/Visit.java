package com.yorix.autometer.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "visit")
@Data
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "description")
    private String description;
}
