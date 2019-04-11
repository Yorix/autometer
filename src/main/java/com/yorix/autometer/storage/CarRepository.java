package com.yorix.autometer.storage;

import com.yorix.autometer.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Integer> {
}
