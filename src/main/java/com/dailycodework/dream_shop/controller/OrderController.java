package com.dailycodework.dream_shop.controller;

import com.dailycodework.dream_shop.dto.OrderDto;
import com.dailycodework.dream_shop.exception.ResourceNotFoundException;
import com.dailycodework.dream_shop.model.Order;
import com.dailycodework.dream_shop.response.ApiResponse;
import com.dailycodework.dream_shop.service.order.IOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"${api.prefix}/orders"})
public class OrderController {

    private final IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }
    @PostMapping("/order")
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId) {
        try {
            Order order = orderService.placeOrder(userId);
            return ResponseEntity.ok(new ApiResponse("Order placed successfully", order));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(500).body(new ApiResponse("Error occurred", e.getMessage()));
        }
    }
    @GetMapping("/order/{orderId}")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId) {
        try {
            OrderDto order = orderService.getOrder(orderId);
            return ResponseEntity.ok(new ApiResponse("Order found successfully", order));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new ApiResponse("Order not found", e.getMessage()));
        }
    }
    @GetMapping("{userId}/orders")
    public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId) {
        try {
            List<OrderDto> orders = orderService.getUserOrders(userId);
            return ResponseEntity.ok(new ApiResponse("Orders found successfully", orders));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(new ApiResponse("Orders not found", e.getMessage()));
        }
    }
}
