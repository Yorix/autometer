package com.yorix.autometer.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "param")
@Data
public class Param {
    @Id
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "value")
    private double value;
}
