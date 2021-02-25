package com.yorix.autometer.storage;

import com.yorix.autometer.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Integer> {
    List<Visit> findAllByTimeBetweenOrderByTimeDesc(LocalDateTime from, LocalDateTime to);
}
