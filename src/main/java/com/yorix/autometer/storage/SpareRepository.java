package com.yorix.autometer.storage;

import com.yorix.autometer.model.Spare;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpareRepository extends JpaRepository<Spare, Integer> {
}
