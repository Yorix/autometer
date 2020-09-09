package com.yorix.autometer.service;

import com.yorix.autometer.model.Lot;
import com.yorix.autometer.storage.LotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LotService extends AppService {
    private final LotRepository lotRepository;

    @Autowired
    public LotService(LotRepository lotRepository) {
        this.lotRepository = lotRepository;
    }

    public Lot get(int id) {
        return lotRepository.getOne(id);
    }

    public List<Lot> getAll() {
        return lotRepository.findAll();
    }

    public void create(Lot lot) {
        lot.setMake(lot.getMake().replaceAll("\\s+", " "));
        lot.setModel(lot.getModel().replaceAll("\\s+", " "));
        lot.setCurrentImg(getAppProperties().getDefaultImageFilename());
        lotRepository.save(lot);
        saveData();
    }
}
