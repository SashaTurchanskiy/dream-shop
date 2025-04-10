package com.dailycodework.dream_shop.service.order;

import com.dailycodework.dream_shop.dto.OrderDto;
import com.dailycodework.dream_shop.exception.ResourceNotFoundException;
import com.dailycodework.dream_shop.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId) throws ResourceNotFoundException;
    OrderDto getOrder(Long orderId) throws ResourceNotFoundException;

    List<OrderDto> getUserOrders(Long userId);
}
