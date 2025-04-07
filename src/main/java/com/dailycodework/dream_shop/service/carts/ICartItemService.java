package com.dailycodework.dream_shop.service.carts;

import com.dailycodework.dream_shop.exception.ProductNotFoundException;
import com.dailycodework.dream_shop.exception.ResourceNotFoundException;
import com.dailycodework.dream_shop.model.CartItem;

public interface ICartItemService {
    void addItemToCart(Long cartId, Long productId, int quantity) throws ProductNotFoundException, ResourceNotFoundException;
    void removeItemFromCart(Long cartId, Long productId) throws ResourceNotFoundException;
    void updateItemQuantity(Long cartId, Long productId, int quantity) throws ResourceNotFoundException;


    CartItem getCartItem(Long cartId, Long productId) throws ResourceNotFoundException;
}
