package com.yorix.carcalculator.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "visit")
@Data
public class Visit {
    @Id
    @GeneratedValue
    private int id;

    @Column
    private String description;
}
