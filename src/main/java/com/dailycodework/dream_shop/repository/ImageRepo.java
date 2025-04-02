package com.dailycodework.dream_shop.repository;

import com.dailycodework.dream_shop.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepo extends JpaRepository<Image, Long> {
}
