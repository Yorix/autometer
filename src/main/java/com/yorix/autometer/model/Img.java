package com.yorix.autometer.model;

import lombok.Data;

import jakarta.persistence.*;

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

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String album;
}
