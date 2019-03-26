package com.yorix.carcalculator.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "note")
@Data
public class Note {
    @Id
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "value")
    private String value;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id", unique = true, nullable = false)
    private Car car;
}
