package com.yorix.autometer.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Img {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "lot_id")
    private Lot lot;

    @Column(nullable = false)
    private String album;
}
