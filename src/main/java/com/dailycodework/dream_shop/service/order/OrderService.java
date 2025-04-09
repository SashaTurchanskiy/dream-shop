package com.dailycodework.dream_shop.service.order;

import com.dailycodework.dream_shop.enums.OrderStatus;
import com.dailycodework.dream_shop.exception.ResourceNotFoundException;
import com.dailycodework.dream_shop.model.Cart;
import com.dailycodework.dream_shop.model.Order;
import com.dailycodework.dream_shop.model.OrderItem;
import com.dailycodework.dream_shop.model.Product;
import com.dailycodework.dream_shop.repository.OrderRepo;
import com.dailycodework.dream_shop.repository.ProductRepository;
import com.dailycodework.dream_shop.service.carts.CartService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
public class OrderService implements IOrderService {

    private final OrderRepo orderRepo;
    private final ProductRepository productRepository;
    private final CartService cartService;

    public OrderService(OrderRepo orderRepo, ProductRepository productRepository, CartService cartService) {
        this.orderRepo = orderRepo;
        this.productRepository = productRepository;
        this.cartService = cartService;
    }
    @Override
    public Order placeOrder(Long userId) throws ResourceNotFoundException {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = createOrder(cart);

        List<OrderItem> orderItems = createOrderItems(order, cart);

        order.setOrderItems(new HashSet<>(orderItems));
        order.setTotalAmount(calculateTotalAmount(orderItems));
        Order savedOrder = orderRepo.save(order);

        cartService.clearCart(cart.getId());
        return savedOrder;
    }

    private Order createOrder(Cart cart){
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart){
        return cart.getItems().stream().map(cartItem ->{
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepository.save(product);
            return new OrderItem(
                    order,
                    product,
                    cartItem.getQuantity(),
                    product.getPrice());
        }).toList();

    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(item -> item.getPrice()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }


    @Override
    public Order getOrder(Long orderId) throws ResourceNotFoundException {
        return orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }
    @Override
    public List<Order> getUserOrders(Long userId) {
        return orderRepo.findByUserId(userId);
    }
}
