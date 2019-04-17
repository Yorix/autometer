package com.yorix.autometer.storage;

import com.yorix.autometer.model.Param;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParamRepository extends JpaRepository<Param, String> {
}
