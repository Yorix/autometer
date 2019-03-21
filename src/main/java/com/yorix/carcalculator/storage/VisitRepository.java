package com.yorix.carcalculator.storage;

import com.yorix.carcalculator.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository extends JpaRepository<Visit, Integer> {
}
