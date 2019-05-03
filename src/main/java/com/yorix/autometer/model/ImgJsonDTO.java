package com.yorix.autometer.model;

import lombok.Getter;

@Getter
public class ImgJsonDTO {
    private String filename;
    private int carId;

    public ImgJsonDTO(Img img) {
        this.filename = img.getFilename();
        this.carId = img.getCar().getId();
    }
}
