package com.yorix.autometer.service;

import com.yorix.autometer.model.Order;
import com.yorix.autometer.storage.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService extends AppService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAll() {
        return orderRepository.findAll(Sort.by("date"));
    }

    public void create(Order order) {
        order.setCurrentImg(getAppProperties().getDefaultImageFilename());
        orderRepository.save(order);
    }
}
