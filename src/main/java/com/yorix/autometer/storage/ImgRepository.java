package com.yorix.autometer.storage;

import com.yorix.autometer.model.Car;
import com.yorix.autometer.model.Img;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImgRepository extends JpaRepository<Img, Integer> {
    List<Img> readAllByCar(Car car);

    void deleteAllByCar(Car car);
}
