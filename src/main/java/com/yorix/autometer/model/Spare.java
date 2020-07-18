package com.yorix.autometer.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "spare")
@Data
public class Spare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "description")
    private String description;

    @Column(name = "buy", nullable = false)
    private double buy;

    @Column(name = "sale", nullable = false)
    private double sale;

    @Column(name = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    public String getFormatDate() {
        if (date != null)
            return date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));
        return "";
    }
}
