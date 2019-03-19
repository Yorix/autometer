package com.yorix.carcalculator.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "entity")
@Data
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "value")
    private String value;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id", unique = true, nullable = false)
    private Car car;
}
