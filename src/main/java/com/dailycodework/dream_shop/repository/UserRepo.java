package com.dailycodework.dream_shop.repository;

import com.dailycodework.dream_shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
