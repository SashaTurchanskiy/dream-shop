package com.dailycodework.dream_shop.service.carts;

import com.dailycodework.dream_shop.exception.ResourceNotFoundException;
import com.dailycodework.dream_shop.model.Cart;
import com.dailycodework.dream_shop.model.CartItem;
import com.dailycodework.dream_shop.repository.CartItemRepo;
import com.dailycodework.dream_shop.repository.CartRepo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CartService implements ICartService {

    private final CartRepo cartRepo;
    private final CartItemRepo cartItemRepo;

    public CartService(CartRepo cartRepo, CartItemRepo cartItemRepo) {
        this.cartRepo = cartRepo;
        this.cartItemRepo = cartItemRepo;
    }

    @Override
    public Cart getCart(Long id) throws ResourceNotFoundException {
        Cart cart = cartRepo.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Cart not found"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepo.save(cart);
    }

    @Override
    public void clearCart(Long id) throws ResourceNotFoundException {
        Cart cart = getCart(id);
        cartItemRepo.deleteAllByCartId(id);
        cart.getCartItems().clear();
        cartRepo.deleteById(id);

    }

    @Override
    public BigDecimal getTotalPrice(Long id) throws ResourceNotFoundException {
        Cart cart = getCart(id);
        return cart.getTotalAmount();
    }
}
