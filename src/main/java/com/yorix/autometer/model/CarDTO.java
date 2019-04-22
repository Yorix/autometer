package com.yorix.autometer.model;

import lombok.Getter;

@Getter
public class CarDTO {
    private int id;
    private String make;
    private String model;
    private String imgFilename;
    private double spending;
    private double income;

    public CarDTO(Car car) {
        this.id = car.getId();
        this.make = car.getMake();
        this.model = car.getModel();
        this.imgFilename = car.getImgFilename();
        this.spending = getSpending(car);
        this.income = getIncome(car);
    }

    private double getSpending(Car car) {
        return car.getNotes()
                .stream()
                .mapToDouble(Note::getValue)
                .filter(value -> value < 0)
                .sum();
    }

    private double getIncome(Car car) {
        return car.getNotes()
                .stream()
                .mapToDouble(Note::getValue)
                .filter(value -> value > 0)
                .sum();
    }
}
