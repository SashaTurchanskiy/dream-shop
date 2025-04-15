package com.dailycodework.dream_shop.service.product;

import com.dailycodework.dream_shop.dto.ProductDto;
import com.dailycodework.dream_shop.exception.AlreadyExistException;
import com.dailycodework.dream_shop.exception.ProductNotFoundException;
import com.dailycodework.dream_shop.exception.ResourceNotFoundException;
import com.dailycodework.dream_shop.model.Product;
import com.dailycodework.dream_shop.request.AddProductRequest;
import com.dailycodework.dream_shop.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {

    Product addProduct(AddProductRequest req) throws AlreadyExistException;
    List<Product> getAllProducts();
    Product getProductById(Long id) throws ProductNotFoundException, ResourceNotFoundException;
    void deleteProductById(Long id) throws ProductNotFoundException;
    Product updateProduct(ProductUpdateRequest req, Long productId) throws ProductNotFoundException, ResourceNotFoundException;
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);
    Long countProductByBrandAndName(String brand, String name);

    List<ProductDto> getConvertedProducts(List<Product> products);

    ProductDto convertToDto(Product product);
}
