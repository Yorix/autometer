package com.yorix.carcalculator.storage;

import com.yorix.carcalculator.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Integer> {

}
