package com.dailycodework.dream_shop.repository;

import com.dailycodework.dream_shop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Long> {
}
