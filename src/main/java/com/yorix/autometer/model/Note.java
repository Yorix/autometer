package com.yorix.autometer.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "note")
@Data
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "description")
    private String description;

    @Column(name = "value", nullable = false)
    private double value;

    @Column(name = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    public String getFormatDate() {
        if (date != null)
            return date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));
        return "";
    }
}
