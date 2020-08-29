package com.yorix.autometer.service;

import com.yorix.autometer.model.Car;
import com.yorix.autometer.model.Order;
import com.yorix.autometer.storage.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService extends AppService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> readAll() {
        return orderRepository.findAll(Sort.by("date"));
    }

    public Order read(int id) {
        return orderRepository.getOne(id);
    }

//    public void create(Order order) {
//        order.setCurrentImg(getAppProperties().getDefaultImageFilename());
//        orderRepository.save(order);
//    }
//
//    public void update(int orderId, Order newOrder) {
//        Order orderFromDb = read(orderId);
//
//        if (!StringUtils.isEmpty(newOrder.getMake()))
//            orderFromDb.setMake(newOrder.getMake().replace(Character.toString(160), " ").trim());
//        if (!StringUtils.isEmpty(newOrder.getModel()))
//            orderFromDb.setModel(newOrder.getModel().replace(Character.toString(160), " ").trim());
//        if (newOrder.getYear() != 0)
//            orderFromDb.setYear(newOrder.getYear());
//        if (!StringUtils.isEmpty(newOrder.getCurrentImg()))
//            orderFromDb.setCurrentImg(newOrder.getCurrentImg());
//        if (newOrder.getNotes() != null)
//            orderFromDb.setNotes(newOrder.getNotes());
//        if (newOrder.getImgs() != null)
//            orderFromDb.setImgs(newOrder.getImgs());
//        if (!StringUtils.isEmpty(newOrder.getLot()))
//            orderFromDb.setLot(newOrder.getLot());
//        if (!StringUtils.isEmpty(newOrder.getVin()))
//            orderFromDb.setVin(newOrder.getVin());
//        if (newOrder.getOdometer() != 0)
//            orderFromDb.setOdometer(newOrder.getOdometer());
//        if (newOrder.getEngine() != 0)
//            orderFromDb.setEngine(newOrder.getEngine());
//        if (!StringUtils.isEmpty(newOrder.getFuel()))
//            orderFromDb.setFuel(newOrder.getFuel());
//        if (!StringUtils.isEmpty(newOrder.getDriveLine()))
//            orderFromDb.setDriveLine(newOrder.getDriveLine());
//        if (!StringUtils.isEmpty(newOrder.getTransmission()))
//            orderFromDb.setTransmission(newOrder.getTransmission());
//        if (!StringUtils.isEmpty(newOrder.getColor()))
//            orderFromDb.setColor(newOrder.getColor());
//        if (!StringUtils.isEmpty(newOrder.getLoss()))
//            orderFromDb.setLoss(newOrder.getLoss());
//        if (!StringUtils.isEmpty(newOrder.getDamage()))
//            orderFromDb.setDamage(newOrder.getDamage());
//        if (!StringUtils.isEmpty(newOrder.getRunAndDrive()))
//            orderFromDb.setRunAndDrive(newOrder.getRunAndDrive());
//        if (!StringUtils.isEmpty(newOrder.getStarts()))
//            orderFromDb.setStarts(newOrder.getStarts());
//        if (!StringUtils.isEmpty(newOrder.getCarKeys()))
//            orderFromDb.setCarKeys(newOrder.getCarKeys());
//
//        orderRepository.save(orderFromDb);
//    }
}
