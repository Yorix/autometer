package com.yorix.carcalculator.storage;

import com.yorix.carcalculator.model.Visit;
import org.springframework.data.repository.CrudRepository;

public interface VisitRepository extends CrudRepository<Visit, Long> {
}
