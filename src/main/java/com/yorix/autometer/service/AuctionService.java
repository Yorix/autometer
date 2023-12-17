package com.yorix.autometer.service;

import com.yorix.autometer.model.Lot;
import com.yorix.autometer.storage.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class AuctionService extends AppService {
    private final AuctionRepository auctionRepository;

    @Autowired
    public AuctionService(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }

    public Lot read(int id) {
        return auctionRepository.getOne(id);
    }

    public List<Lot> readAll() {
        return auctionRepository.findAll();
    }

    public void create(Lot lot) {
        lot.setMake(lot.getMake().replaceAll("\\s+", " "));
        lot.setModel(lot.getModel().replaceAll("\\s+", " "));
        lot.setCurrentImg(getAppProperties().getDefaultCarImageFilename());
        auctionRepository.save(lot);
        saveData();
    }

    public void update(int id, Lot newLot) {
        Lot lotFromDb = read(id);

        if (StringUtils.hasText(newLot.getMake()))
            lotFromDb.setMake(newLot.getMake().replace(Character.toString(160), " ").trim());
        if (StringUtils.hasText(newLot.getModel()))
            lotFromDb.setModel(newLot.getModel().replace(Character.toString(160), " ").trim());
        if (newLot.getYear() != 0)
            lotFromDb.setYear(newLot.getYear());
        if (StringUtils.hasText(newLot.getCurrentImg()))
            lotFromDb.setCurrentImg(newLot.getCurrentImg());
        if (newLot.getImgs() != null)
            lotFromDb.setImgs(newLot.getImgs());
        if (newLot.getLot() != 0)
            lotFromDb.setLot(newLot.getLot());
        if (StringUtils.hasText(newLot.getVin()))
            lotFromDb.setVin(newLot.getVin());
        if (newLot.getOdometer() != 0)
            lotFromDb.setOdometer(newLot.getOdometer());
        if (newLot.getEngine() != 0)
            lotFromDb.setEngine(newLot.getEngine());
        if (StringUtils.hasText(newLot.getFuel()))
            lotFromDb.setFuel(newLot.getFuel());
        if (StringUtils.hasText(newLot.getDriveLine()))
            lotFromDb.setDriveLine(newLot.getDriveLine());
        if (StringUtils.hasText(newLot.getTransmission()))
            lotFromDb.setTransmission(newLot.getTransmission());
        if (StringUtils.hasText(newLot.getColor()))
            lotFromDb.setColor(newLot.getColor());
        if (StringUtils.hasText(newLot.getLoss()))
            lotFromDb.setLoss(newLot.getLoss());
        if (StringUtils.hasText(newLot.getDamage()))
            lotFromDb.setDamage(newLot.getDamage());
        if (StringUtils.hasText(newLot.getRunAndDrive()))
            lotFromDb.setRunAndDrive(newLot.getRunAndDrive());
        if (StringUtils.hasText(newLot.getStarts()))
            lotFromDb.setStarts(newLot.getStarts());
        if (StringUtils.hasText(newLot.getCarKeys()))
            lotFromDb.setCarKeys(newLot.getCarKeys());
        if (StringUtils.hasText(newLot.getUserPhone()))
            lotFromDb.setUserPhone(newLot.getUserPhone());
        lotFromDb.setCurrentBid(newLot.getCurrentBid());

        auctionRepository.save(lotFromDb);
        saveData();
    }

    public void delete(int id) {
        auctionRepository.deleteById(id);
        saveData();
    }

    public boolean checkIp(int lotId, String clientIp) {
        Lot lot = auctionRepository.getOne(lotId);
        if (lot.getIps().contains(clientIp))
            return true;
        lot.getIps().add(clientIp);
        return false;
    }

    public void clearIps(Lot lot) {
        lot.getIps().clear();
        auctionRepository.save(lot);
    }
}
