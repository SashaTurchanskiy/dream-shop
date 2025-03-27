package com.dailycodework.dream_shop.service.product;

import com.dailycodework.dream_shop.exception.ProductNotFoundException;
import com.dailycodework.dream_shop.model.Product;
import com.dailycodework.dream_shop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService{

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product addProduct(Product product) {
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) throws ProductNotFoundException {
        return productRepository.findById(id).orElseThrow(
                ()-> new ProductNotFoundException("Product not found"));
    }

    @Override
    public void deleteProductById(Long id)  {
        productRepository.findById(id).ifPresentOrElse(productRepository::delete,
                () -> new ProductNotFoundException("Product not found"));

    }

    @Override
    public void updateProduct(Product product, Long productId) {

    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return List.of();
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return List.of();
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return List.of();
    }

    @Override
    public Long countProductByBrandAndName(String brand, String name) {
        return 0L;
    }
}
