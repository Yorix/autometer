package com.yorix.autometer.service;

import com.yorix.autometer.model.Spare;
import com.yorix.autometer.storage.SpareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;

@Service
public class SpareService extends AppService {
    private final SpareRepository spareRepository;

    @Autowired
    public SpareService(SpareRepository spareRepository) {
        this.spareRepository = spareRepository;
    }

    public List<Spare> getAll() {
        return spareRepository.findAll(Sort.by("date"));
    }

    public void create(Spare spare) {
        spareRepository.save(spare);
        saveData();
    }

    public void update(int spareId, Spare newSpare) {
        Spare spareFromDb = spareRepository.getOne(spareId);
        LocalDate newSpareDate = newSpare.getDate();
        String newSpareDescription = newSpare.getDescription();
        double newSpareBuy = newSpare.getBuy();
        double newSpareSale = newSpare.getSale();

        if (StringUtils.hasText(newSpareDescription))
            spareFromDb.setDescription(newSpareDescription);
        spareFromDb.setDate(newSpareDate);
        spareFromDb.setBuy(newSpareBuy);
        spareFromDb.setSale(newSpareSale);

        spareRepository.save(spareFromDb);
        saveData();
    }

    public void delete(int id) {
        spareRepository.deleteById(id);
    }
}
