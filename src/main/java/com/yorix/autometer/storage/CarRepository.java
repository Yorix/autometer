package com.yorix.autometer.storage;

import com.yorix.autometer.model.Car;
import com.yorix.autometer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Integer> {
    List<Car> findAllByUserOrderByIdDesc(User user);

    List<Car> findAllByUserNotOrderByIdDesc(User user);
}
