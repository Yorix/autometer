package com.yorix.autometer.storage;

import com.yorix.autometer.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository extends JpaRepository<Visit, Integer> {
}
