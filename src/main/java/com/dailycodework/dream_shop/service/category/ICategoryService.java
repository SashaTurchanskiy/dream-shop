package com.dailycodework.dream_shop.service.category;

import com.dailycodework.dream_shop.exception.AlreadyExistException;
import com.dailycodework.dream_shop.exception.ResourceNotFoundException;
import com.dailycodework.dream_shop.model.Category;

import java.util.List;

public interface ICategoryService {
    Category getCategoryById(Long id) throws Exception;
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(Category category) throws AlreadyExistException;
    Category updateCategory(Category category, Long id) throws Exception;
    void deleteCategory(Long id) throws ResourceNotFoundException;
}
