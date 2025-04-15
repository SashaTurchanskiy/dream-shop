package com.dailycodework.dream_shop.controller;

import com.dailycodework.dream_shop.exception.ProductNotFoundException;
import com.dailycodework.dream_shop.exception.ResourceNotFoundException;
import com.dailycodework.dream_shop.model.Cart;
import com.dailycodework.dream_shop.model.User;
import com.dailycodework.dream_shop.response.ApiResponse;
import com.dailycodework.dream_shop.service.carts.ICartItemService;
import com.dailycodework.dream_shop.service.carts.ICartService;
import com.dailycodework.dream_shop.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/cart-items")
public class CartItemController {

    private final ICartItemService cartItemService;
    private final ICartService cartService;
    private final UserService userService;

    public CartItemController(ICartItemService cartItemService, ICartService cartService, UserService userService, UserService userService1) {
        this.cartItemService = cartItemService;
        this.cartService = cartService;
        this.userService = userService1;
    }
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addItemToCart(
                                                     @RequestParam Long productId,
                                                     @RequestParam Integer quantity) {


        try {
                User user = userService.getUserById(4L);
             Cart cart = cartService.initializeNewCart(user);

            cartItemService.addItemToCart(cart.getId(), productId, quantity);
            return ResponseEntity.ok(new ApiResponse("Item added to cart successfully", null));
        } catch (ProductNotFoundException | ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(), "Item not found"));

        }
    }
    @DeleteMapping("/cart/{cartId}/item/{itemId}/remove")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId,
                                                          @PathVariable Long itemId) {
        try {
            cartItemService.removeItemFromCart(cartId, itemId);
            return ResponseEntity.ok(new ApiResponse("Item removed from cart successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(), "Item not found"));
        }
    }
    @PutMapping("/cart/{cartId}/item/{itemId}/update")
    public ResponseEntity<ApiResponse> updateCartItem(@PathVariable Long cartId,
                                                      @PathVariable Long itemId,
                                                      @RequestParam Integer quantity) {
        try {
            cartItemService.updateItemQuantity(cartId, itemId, quantity);
            return ResponseEntity.ok(new ApiResponse("Item updated successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(), "Item not found"));
        }
    }

}
