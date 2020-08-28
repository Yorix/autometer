package com.yorix.autometer.controller;

import com.yorix.autometer.model.Order;
import com.yorix.autometer.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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
        List<Order> orders = orderService.getAll();
        double sum = orders.stream().mapToDouble(spare -> spare.getBuy() + spare.getSale()).sum();
        modelAndView.addObject("budget", orderService.getBudget());
        modelAndView.addObject("balance", orderService.getBalance());
        modelAndView.addObject("sum", sum);
        modelAndView.addObject("orders", orders);
        modelAndView.addObject("order", new Order());
        return modelAndView;
    }

    @PostMapping
    public String create(Order order) {
        orderService.create(order);
        return "redirect:/orders/";
    }
}
