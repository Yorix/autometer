package com.yorix.autometer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "param")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Param {
    @Id
    @Column(length = 50)
    private String name;

    @Column(name = "value")
    private double value;
}
