package com.yorix.autometer.model;

import lombok.Getter;

@Getter
public class CarJsonDTO {
    private int id;
    private String make;
    private String model;
    private String imgFilename;

    public CarJsonDTO(Car car) {
        this.id = car.getId();
        this.make = car.getMake();
        this.model = car.getModel();
        this.imgFilename = car.getImgFilename();
    }
}
