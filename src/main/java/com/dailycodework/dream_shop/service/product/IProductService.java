package com.dailycodework.dream_shop.service.product;

import com.dailycodework.dream_shop.exception.ProductNotFoundException;
import com.dailycodework.dream_shop.model.Product;

import java.util.List;

public interface IProductService {

    Product addProduct(Product product);
    List<Product> getAllProducts();
    Product getProductById(Long id) throws ProductNotFoundException;
    void deleteProductById(Long id) throws ProductNotFoundException;
    void updateProduct(Product product, Long productId);
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);
    Long countProductByBrandAndName(String brand, String name);



    


}
