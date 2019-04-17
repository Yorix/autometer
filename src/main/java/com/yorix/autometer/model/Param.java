package com.yorix.autometer.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "param")
@Data
public class Param {
    @Id
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "value")
    private int value;
}
