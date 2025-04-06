package com.dailycodework.dream_shop.service.carts;

import com.dailycodework.dream_shop.exception.ProductNotFoundException;
import com.dailycodework.dream_shop.exception.ResourceNotFoundException;
import com.dailycodework.dream_shop.model.Cart;
import com.dailycodework.dream_shop.model.CartItem;
import com.dailycodework.dream_shop.model.Product;
import com.dailycodework.dream_shop.repository.CartItemRepo;
import com.dailycodework.dream_shop.repository.CartRepo;
import com.dailycodework.dream_shop.service.product.IProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CartItemService implements ICartItemService {

    private final CartItemRepo cartItemRepo;
    private final IProductService productService;
    private final ICartService cartService;
    private final CartRepo cartRepo;

    public CartItemService(CartItemRepo cartItemRepo, IProductService productService, ICartService cartService, CartRepo cartRepo) {
        this.cartItemRepo = cartItemRepo;
        this.productService = productService;
        this.cartService = cartService;
        this.cartRepo = cartRepo;
    }

    @Override
    public void addCartToItem(Long cartId, Long productId, int quantity) throws ProductNotFoundException, ResourceNotFoundException {
        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);
        CartItem cartItem = cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElse(new CartItem());
        if (cartItem.getId() == null) {
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
           cartItem.setUnitPrice(product.getPrice());
        }
        else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepo.save(cartItem);
        cartRepo.save(cart);

    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) throws ResourceNotFoundException {
        Cart cart = cartService.getCart(cartId);
        CartItem cartItem = getCartitem(cartId, productId);
        cart.removeItem(cartItem);
        cartRepo.save(cart);

    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) throws ResourceNotFoundException {
        Cart cart = cartService.getCart(cartId);
        cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                        .ifPresent(item -> {
                                item.setQuantity(quantity);
                        item.setUnitPrice(item.getProduct().getPrice());
                        item.setTotalPrice();
                        });
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        cartRepo.save(cart);

    }
    @Override
    public CartItem getCartitem(Long cartId, Long productId) throws ResourceNotFoundException {
        Cart cart = cartService.getCart(cartId);
       return cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElseThrow(() -> new ResourceNotFoundException(" Item not found"));

    }
}
