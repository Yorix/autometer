package com.yorix.autometer.storage;

import com.yorix.autometer.model.Lot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<Lot, Integer> {
}
