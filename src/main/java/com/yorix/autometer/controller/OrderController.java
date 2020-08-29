package com.yorix.autometer.controller;

import com.yorix.autometer.model.CarViewDTO;
import com.yorix.autometer.model.Order;
import com.yorix.autometer.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/orders/")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ModelAndView getAll() {
        ModelAndView modelAndView = new ModelAndView("order-list");
        List<Order> orders = orderService.readAll();
//        double sum = orders.stream().mapToDouble(CarViewDTO::getSpending).sum();
        modelAndView.addObject("budget", orderService.getBudget());
        modelAndView.addObject("balance", orderService.getBalance());
//        modelAndView.addObject("sum", sum);
        modelAndView.addObject("orders", orders);
        return modelAndView;
    }

    @PostMapping
    public String create(Order order) {
//        orderService.create(order);
        orderService.saveData();
        return "redirect:/orders/";
    }

    @PutMapping("{orderId}/")
    public String update(@PathVariable int orderId, Order order) {
//        orderService.update(orderId, order);
        orderService.saveData();
        return "redirect:/orders/";
    }
}
