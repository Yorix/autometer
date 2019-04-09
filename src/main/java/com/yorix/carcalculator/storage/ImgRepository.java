package com.yorix.carcalculator.storage;

import com.yorix.carcalculator.model.Car;
import com.yorix.carcalculator.model.Img;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImgRepository extends JpaRepository<Img, String> {
    Img readByFilename(String filename);

    List<Img> readAllByCar(Car car);

    void deleteAllByCar(Car car);
}
