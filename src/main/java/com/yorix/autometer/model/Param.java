package com.yorix.autometer.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "param")
@Data
public class Param {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 191)
    private String name;

    @Column(name = "value")
    private double value;
}
