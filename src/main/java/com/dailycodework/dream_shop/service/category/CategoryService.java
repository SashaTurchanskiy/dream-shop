package com.dailycodework.dream_shop.service.category;

import com.dailycodework.dream_shop.exception.AlreadyExistException;
import com.dailycodework.dream_shop.exception.ResourceNotFoundException;
import com.dailycodework.dream_shop.model.Category;
import com.dailycodework.dream_shop.repository.CategoryRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService {

    private final CategoryRepo categoryRepo;

    public CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public Category getCategoryById(Long id) throws Exception {
        return categoryRepo.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Category not found"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepo.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public Category addCategory(Category category) throws AlreadyExistException {
        return Optional.of(category).filter(c -> !categoryRepo.existsByName(c.getName()))
                .map(categoryRepo :: save).orElseThrow(()->
                        new AlreadyExistException(category.getName() + "Category already exists"));
    }

    @Override
    public Category updateCategory(Category category, Long id) throws Exception {
        return Optional.ofNullable(getCategoryById(id)).map(oldCategory ->{
            oldCategory.setName(category.getName());
            return categoryRepo.save(oldCategory);
        }) . orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    @Override
    public void deleteCategory(Long id){
        categoryRepo.findById(id).ifPresentOrElse(categoryRepo :: delete, () ->
                new ResourceNotFoundException("Category not found"));

    }
}
