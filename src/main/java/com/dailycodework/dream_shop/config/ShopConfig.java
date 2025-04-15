package com.dailycodework.dream_shop.config;

import com.dailycodework.dream_shop.dto.*;
import com.dailycodework.dream_shop.model.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShopConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
//
//        // Configure global settings
//        modelMapper.getConfiguration()
//                .setMatchingStrategy(MatchingStrategies.STRICT)
//                .setFieldMatchingEnabled(true)
//                .setSkipNullEnabled(true)
//                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
//
//        // Create empty type maps first
//        modelMapper.emptyTypeMap(Cart.class, CartDto.class);
//        modelMapper.emptyTypeMap(CartItem.class, CartItemDto.class);
//        modelMapper.emptyTypeMap(User.class, UserDto.class);
//        modelMapper.emptyTypeMap(Order.class, OrderDto.class);
//        modelMapper.emptyTypeMap(OrderItem.class, OrderItemDto.class);
//        modelMapper.emptyTypeMap(Product.class, ProductDto.class);
//        modelMapper.emptyTypeMap(Category.class, CategoryDto.class);
//
//        // Configure Cart -> CartDto mapping
//        modelMapper.typeMap(Cart.class, CartDto.class)
//                .addMappings(mapper -> {
//                    mapper.map(src -> src.getUser().getId(), CartDto::setUserId);
//                    mapper.map(Cart::getItems, CartDto::setItems);
//                    mapper.map(Cart::getTotalAmount, CartDto::setTotalAmount);
//                });
//
//        // Configure CartItem -> CartItemDto mapping
//        modelMapper.typeMap(CartItem.class, CartItemDto.class)
//                .addMappings(mapper -> {
//                    mapper.map(src -> src.getCart().getId(), CartItemDto::setCartId);
//                    mapper.map(CartItem::getQuantity, CartItemDto::setQuantity);
//                    mapper.map(CartItem::getUnitPrice, CartItemDto::setUnitPrice);
//                    mapper.map(CartItem::getTotalPrice, CartItemDto::setTotalPrice);
//                    mapper.map(CartItem::getProduct, CartItemDto::setProduct);
//                });
//
//        // Configure User -> UserDto mapping
//        modelMapper.typeMap(User.class, UserDto.class)
//                .addMappings(mapper -> {
//                    mapper.map(User::getId, UserDto::setId);
//                    mapper.map(User::getFirstName, UserDto::setFirstName);
//                    mapper.map(User::getLastName, UserDto::setLastName);
//                    mapper.map(User::getEmail, UserDto::setEmail);
//                    mapper.map(User::getCart, UserDto::setCart);
//                });
//
//        // Configure Order -> OrderDto mapping
//        modelMapper.typeMap(Order.class, OrderDto.class)
//                .addMappings(mapper -> {
//                    mapper.map(src -> src.getUser().getId(), OrderDto::setUserId);
//                    mapper.map(src -> src.getUser().getEmail(), OrderDto::setUserEmail);
//                    mapper.map(src -> src.getUser().getFirstName() + " " + src.getUser().getLastName(),
//                             OrderDto::setUserName);
//                    mapper.map(Order::getOrderItems, OrderDto::setOrderItems);
//                });
//
//        // Configure OrderItem -> OrderItemDto mapping
//        modelMapper.typeMap(OrderItem.class, OrderItemDto.class)
//                .addMappings(mapper -> {
//                    mapper.map(OrderItem::getId, OrderItemDto::setId);
//                    mapper.map(OrderItem::getQuantity, OrderItemDto::setQuantity);
//                    mapper.map(OrderItem::getPrice, OrderItemDto::setPrice);
//                    mapper.map(OrderItem::getProduct, OrderItemDto::setProduct);
//                });
//
//        // Configure Product -> ProductDto mapping
//        modelMapper.typeMap(Product.class, ProductDto.class)
//                .addMappings(mapper -> {
//                    mapper.map(Product::getId, ProductDto::setId);
//                    mapper.map(Product::getName, ProductDto::setName);
//                    mapper.map(Product::getDescription, ProductDto::setDescription);
//                    mapper.map(Product::getPrice, ProductDto::setPrice);
//                    mapper.map(Product::getInventory, ProductDto::setInventory);
//                    mapper.map(Product::getCategory, ProductDto::setCategory);
//                });
//
//        // Configure Category -> CategoryDto mapping
//        modelMapper.typeMap(Category.class, CategoryDto.class)
//                .addMappings(mapper -> {
//                    mapper.map(Category::getId, CategoryDto::setId);
//                    mapper.map(Category::getName, CategoryDto::setName);
//                });

        return modelMapper;
    }
}
