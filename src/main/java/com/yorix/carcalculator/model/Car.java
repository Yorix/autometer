package com.yorix.carcalculator.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "car")
@Data
@ToString(exclude = "notes")
@Component
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "make", nullable = false)
    private String make;

    @Column(name = "model", nullable = false)
    @NotNull
    private String model;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "car")
    private Set<Note> notes;

    @Column(name = "img_filename")
    private String imgFilename;
}
