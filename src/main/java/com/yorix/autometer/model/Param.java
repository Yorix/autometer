package com.yorix.autometer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Param {
    @Id
    @Column(length = 50)
    private String name;
    @Column(columnDefinition = "decimal(10,2)")
    private double value;
}
