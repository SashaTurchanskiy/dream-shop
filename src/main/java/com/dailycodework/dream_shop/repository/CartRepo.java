package com.dailycodework.dream_shop.repository;

import com.dailycodework.dream_shop.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartRepo extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId);

}
