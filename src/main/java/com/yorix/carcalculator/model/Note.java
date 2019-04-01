package com.yorix.carcalculator.model;

import lombok.Data;

import javax.persistence.*;
import java.time.*;

@Entity
@Table(name = "note")
@Data
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "description")
    private String description;

    @Column(name = "value")
    private double value;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @Column(name = "date")
    private LocalDateTime date = ZonedDateTime.now(ZoneOffset.ofHours(3)).toLocalDateTime();
}
