package com.vfort.controller;

import com.vfort.model.entity.FailedOrder;
import com.vfort.repository.FailedOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FailedOrderController {

    @Autowired
    private FailedOrderRepository repository;

    @GetMapping("/failed-orders")
    public List<FailedOrder> getFailedOrders() {
        return repository.findAll();
    }
}