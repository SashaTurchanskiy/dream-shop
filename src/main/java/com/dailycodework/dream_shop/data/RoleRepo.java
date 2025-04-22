package com.dailycodework.dream_shop.data;

import com.dailycodework.dream_shop.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String role);
}
