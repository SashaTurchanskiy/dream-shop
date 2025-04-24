package com.dailycodework.dream_shop.controller;

import com.dailycodework.dream_shop.dto.ProductDto;
import com.dailycodework.dream_shop.exception.AlreadyExistException;
import com.dailycodework.dream_shop.exception.ProductNotFoundException;
import com.dailycodework.dream_shop.exception.ResourceNotFoundException;
import com.dailycodework.dream_shop.model.Product;
import com.dailycodework.dream_shop.request.AddProductRequest;
import com.dailycodework.dream_shop.request.ProductUpdateRequest;
import com.dailycodework.dream_shop.response.ApiResponse;
import com.dailycodework.dream_shop.service.product.IProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }
    @GetMapping("/all")
    private ResponseEntity<ApiResponse> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
        return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId) throws ProductNotFoundException, ResourceNotFoundException {
        try {
            var product = productService.getProductById(productId);
            var productDto = productService.convertToDto(product);
            if (product == null) {
                throw new ProductNotFoundException("Product not found");
            }
            return ResponseEntity.ok(new ApiResponse("success", productDto));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(), null));
        }
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product) {
        try {
            Product theProduct = productService.addProduct(product);
            return ResponseEntity.ok(new ApiResponse("Product added successfully", theProduct));
        } catch (AlreadyExistException e) {
            return ResponseEntity.status(409).body(new ApiResponse("Failed to add product", e.getMessage()));
        }
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/product/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long productId, @RequestBody ProductUpdateRequest req){
        try {
            Product updProduct = productService.updateProduct(req, productId);
            return ResponseEntity.ok(new ApiResponse("Product updated successfully", updProduct));
        } catch (ResourceNotFoundException | ProductNotFoundException e) {
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(), null));
        }
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId){
        try {
            productService.deleteProductById(productId);
            return ResponseEntity.ok(new ApiResponse("Product deleted successfully", null));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(), null));
        }
    }
    @GetMapping("/products/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brandName, @RequestParam String productName) {
        try {
            List<Product> product = productService.getProductsByBrandAndName(brandName, productName);
            if (product.isEmpty()) {
                return ResponseEntity.status(404).body(new ApiResponse("Product not found", null));
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(product);
            return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
        }catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse("Failed to fetch products", e.getMessage()));
        }
    }
    @GetMapping("/products/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String category, @RequestParam String brand) {

        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(category, brand);
            if (products.isEmpty()) {
                return ResponseEntity.status(404).body(new ApiResponse("Product not found", null));
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse("Failed to fetch products", e.getMessage()));
        }
    }
    @GetMapping("/products/{name}/products")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name){
        try {
            List<Product> products = productService.getProductsByName(name);
            if (products.isEmpty()) {
                return ResponseEntity.status(404).body(new ApiResponse("Product not found", null));
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse("Failed to fetch products", e.getMessage()));
        }
    }
    @GetMapping("/product/by-brand")
    public ResponseEntity<ApiResponse> findProductByBrand(@RequestParam String brand) {
        try {
            List<Product> products = productService.getProductsByBrand(brand);
            if (products.isEmpty()) {
                return ResponseEntity.status(404).body(new ApiResponse("Product not found", null));
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse("Failed to fetch products", e.getMessage()));
        }
    }
    @GetMapping("/product/{category}/all/products")
    public ResponseEntity<ApiResponse> findProductByCategory(@PathVariable String category) {
        try {
            List<Product> products = productService.getProductsByCategory(category);
            if (products.isEmpty()) {
                return ResponseEntity.status(404).body(new ApiResponse("Product not found", null));
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse("Failed to fetch products", e.getMessage()));
        }
    }
    @GetMapping("/product/count/by-brand/and-name")
    public ResponseEntity<ApiResponse> countProductByBrandAndName(@RequestParam String brand, @RequestParam String name) {
        try {
            var productCount = productService.countProductByBrandAndName(brand, name);
            return ResponseEntity.ok(new ApiResponse("success", productCount));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse("Failed to fetch products", e.getMessage()));
        }
    }
}
