package com.dailycodework.dream_shop.service.product;

import com.dailycodework.dream_shop.dto.ImageDto;
import com.dailycodework.dream_shop.dto.ProductDto;
import com.dailycodework.dream_shop.exception.ResourceNotFoundException;
import com.dailycodework.dream_shop.model.Category;
import com.dailycodework.dream_shop.model.Image;
import com.dailycodework.dream_shop.model.Product;
import com.dailycodework.dream_shop.repository.CategoryRepo;
import com.dailycodework.dream_shop.repository.ImageRepo;
import com.dailycodework.dream_shop.repository.ProductRepository;
import com.dailycodework.dream_shop.request.AddProductRequest;

import com.dailycodework.dream_shop.request.ProductUpdateRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
public class ProductService implements IProductService{

    private final ProductRepository productRepository;
    private final CategoryRepo categoryRepo;
    private final ModelMapper modelMapper;
    private final ImageRepo imageRepo;

    public ProductService(ProductRepository productRepository, CategoryRepo categoryRepo, ModelMapper modelMapper, ImageRepo imageRepo) {
        this.productRepository = productRepository;
        this.categoryRepo = categoryRepo;
        this.modelMapper = modelMapper;
        this.imageRepo = imageRepo;
    }

    @Override
    public Product addProduct(AddProductRequest req) {
        Category category = Optional.ofNullable(categoryRepo.findByName(req.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(req.getCategory().getName());
                    return categoryRepo.save(newCategory);
                });
        req.setCategory(category);
        return productRepository.save(createProduct(req, category));
    }

    private Product createProduct(AddProductRequest req, Category category) {
        return new Product(
                req.getName(),
                req.getBrand(),
                req.getPrice(),
                req.getInventory(),
                req.getDescription(),
                category
        );

    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) throws ResourceNotFoundException {
        return productRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Product not found"));
    }

    @Override
    public void deleteProductById(Long id)  {
        productRepository.findById(id).ifPresentOrElse(productRepository::delete,
                () -> new ResourceNotFoundException("Product not found"));

    }

    @Override
    public Product updateProduct(ProductUpdateRequest req, Long productId) throws  ResourceNotFoundException {
        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct, req))
                .map(productRepository::save)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest req) {
        existingProduct.setName(req.getName());
        existingProduct.setBrand(req.getBrand());
        existingProduct.setDescription(req.getDescription());
        existingProduct.setPrice(req.getPrice());
        existingProduct.setInventory(req.getInventory());

        Category category = categoryRepo.findByName(req.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;
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
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }

    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products) {
        return products.stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public ProductDto convertToDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<Image> images = imageRepo.findByProductId(product.getId());
        List<ImageDto> imageDto = images.stream()
                .map(image -> modelMapper.map(image, ImageDto.class))
                .toList();
        productDto.setImages(imageDto);
        return productDto;
    }
}
