package com.yorix.autometer.storage;

import com.yorix.autometer.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Integer> {
    List<Car> findAllByOrderByIdDesc();
}
