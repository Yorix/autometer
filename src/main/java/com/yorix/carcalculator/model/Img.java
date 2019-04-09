package com.yorix.carcalculator.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "img")
@Data
public class Img {
    @Id
    @Column(name = "filename", nullable = false)
    private String filename;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;
}
