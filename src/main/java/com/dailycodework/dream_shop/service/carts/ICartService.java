package com.dailycodework.dream_shop.service.carts;

import com.dailycodework.dream_shop.exception.ResourceNotFoundException;
import com.dailycodework.dream_shop.model.Cart;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id) throws ResourceNotFoundException;
    void clearCart(Long id) throws ResourceNotFoundException;
    BigDecimal getTotalPrice(Long id) throws ResourceNotFoundException;
    Long  initializeNewCart();

    Cart getCartByUserId(Long userId);
}
