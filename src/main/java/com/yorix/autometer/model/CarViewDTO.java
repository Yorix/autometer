package com.yorix.autometer.model;

import lombok.Getter;

@Getter
public class CarViewDTO {
    private final int id;
    private final boolean ord;
    private final String make;
    private final String model;
    private final int year;
    private final int lot;
    private final String vin;
    private final int odometer;
    private final double engine;
    private final String fuel;
    private final String driveLine;
    private final String transmission;
    private final String color;
    private final String loss;
    private final String damage;
    private final String runAndDrive;
    private final String starts;
    private final String carKeys;
    private final String currentImg;
    private final double spending;
    private final double income;

    public CarViewDTO(Car car) {
        this.id = car.getId();
        this.ord = car.isOrd();
        this.make = car.getMake();
        this.model = car.getModel();
        this.year = car.getYear();
        this.lot = car.getLot();
        this.vin = car.getVin();
        this.odometer = car.getOdometer();
        this.engine = car.getEngine();
        this.fuel = car.getFuel();
        this.driveLine = car.getDriveLine();
        this.transmission = car.getTransmission();
        this.color = car.getColor();
        this.loss = car.getLoss();
        this.damage = car.getDamage();
        this.runAndDrive = car.getRunAndDrive();
        this.starts = car.getStarts();
        this.carKeys = car.getCarKeys();
        this.currentImg = car.getCurrentImg();
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
