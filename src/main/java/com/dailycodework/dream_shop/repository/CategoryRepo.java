package com.dailycodework.dream_shop.repository;

import com.dailycodework.dream_shop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Long> {
    Category findByName(String name);

    boolean existsByName(String name);
}
