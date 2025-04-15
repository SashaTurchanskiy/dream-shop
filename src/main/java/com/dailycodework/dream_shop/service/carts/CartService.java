package com.dailycodework.dream_shop.service.carts;

import com.dailycodework.dream_shop.exception.ResourceNotFoundException;
import com.dailycodework.dream_shop.model.Cart;
import com.dailycodework.dream_shop.model.User;
import com.dailycodework.dream_shop.repository.CartItemRepo;
import com.dailycodework.dream_shop.repository.CartRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CartService implements ICartService {

    private final CartRepo cartRepo;
    private final CartItemRepo cartItemRepo;
    private final AtomicLong cartIdGenerator = new AtomicLong(0);

    public CartService(CartRepo cartRepo, CartItemRepo cartItemRepo) {
        this.cartRepo = cartRepo;
        this.cartItemRepo = cartItemRepo;
    }

    @Override
    public Cart getCart(Long id) throws ResourceNotFoundException {
        Cart cart = cartRepo.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Cart not found"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepo.save(cart);
    }

    @Transactional
    @Override
    public void clearCart(Long id) throws ResourceNotFoundException {
        Cart cart = getCart(id);
        cartItemRepo.deleteAllByCartId(id);
        cart.getItems().clear();
        cartRepo.deleteById(id);

    }

    @Override
    public BigDecimal getTotalPrice(Long id) throws ResourceNotFoundException {
        Cart cart = getCart(id);
        return cart.getTotalAmount();
    }

    @Override
    public Cart  initializeNewCart(User user) {
        return Optional.ofNullable(getCartByUserId(user.getId()))
                .orElseGet(()-> {
                    Cart cart = new Cart();
                    cart.setUser(user);
                    return cartRepo.save(cart);
                });

    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepo.findByUserId(userId);
    }
}
