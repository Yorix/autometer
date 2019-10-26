package com.yorix.autometer.model;

import lombok.Getter;

@Getter
public class CarViewDTO {
    private int id;
    private String make;
    private String model;
    private int year;
    private int lot;
    private String vin;
    private int odometer;
    private double engine;
    private String fuel;
    private String driveLine;
    private String transmission;
    private String color;
    private String loss;
    private String damage;
    private String runAndDrive;
    private String starts;
    private String carKeys;
    private String currentImg;
    private double spending;
    private double income;

    public CarViewDTO(Car car) {
        this.id = car.getId();
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
