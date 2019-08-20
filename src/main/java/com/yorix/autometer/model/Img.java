package com.yorix.autometer.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "img")
@Data
public class Img {
    @Id
    @Column(length = 191)
    private String filename;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;
}
