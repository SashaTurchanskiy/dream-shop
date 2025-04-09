package com.dailycodework.dream_shop.repository;

import com.dailycodework.dream_shop.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepo extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId);
}
