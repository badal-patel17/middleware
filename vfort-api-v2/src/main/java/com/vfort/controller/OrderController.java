package com.vfort.controller;

import com.vfort.model.dto.OrderDTO;
import com.vfort.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST endpoints for Orders.
 * Base URL: http://localhost:8090/vfort/api/orders
 */
@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:85")  // Allow React dev server
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * GET /api/orders
     * GET /api/orders?status=PENDING
     *
     * Returns all orders, optionally filtered by status.
     * Used for the orders list/dashboard view in React.
     */
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getOrders(
            @RequestParam(required = false) String status) {

//        List<OrderDTO> orders = (status != null)
//                ? orderService.getOrdersByStatus(status)
//                : orderService.getAllOrders();

        List<OrderDTO> orders = orderService.getAllOrders();

        return ResponseEntity.ok(orders);
    }

    /**
     * GET /api/orders/{orderId}
     *
     * Returns full order detail with:
     * - Oracle business data
     * - All linked fallouts
     * - Flowable process/task status for each fallout
     *
     * Used for the order detail view in React.
     */
//    @GetMapping("/{orderId}")
//    public ResponseEntity<OrderDTO> getOrderById(@PathVariable String orderId) {
//        return ResponseEntity.ok(orderService.getOrderById(orderId));
//    }
}
