package com.yorix.autometer.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "img")
@Data
public class Img {
    @Id
    @Column(name = "filename", nullable = false)
    private String filename;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;
}
