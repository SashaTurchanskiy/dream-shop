package com.dailycodework.dream_shop.data;

import com.dailycodework.dream_shop.model.Role;
import com.dailycodework.dream_shop.model.User;
import com.dailycodework.dream_shop.repository.UserRepo;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataInitializer implements ApplicationListener {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;

    public DataInitializer(UserRepo userRepo, PasswordEncoder passwordEncoder, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.roleRepo = roleRepo;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        Set<String> defaultRoles = Set.of("ROLE_ADMIN", "ROLE_CUSTOMER");
        createDefaultUserIfNotExists();
        createDefaultRoleIfNotExists(defaultRoles);
    }

    private void createDefaultUserIfNotExists() {
        for (int i = 0; i < 5; i++) {
            String defaultEmail = "user" + i + "@example.com";
            if (userRepo.existsByEmail(defaultEmail)) {
                continue;
            }
            User user = new User();
            user.setFirstName("User");
            user.setLastName("User" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            userRepo.save(user);
            System.out.println("Default vet user " + i + " created");
        }
    }
    private void createDefaultRoleIfNotExists(Set<String> roles) {
        roles.stream()
                .filter(role -> roleRepo.findByName(role).isEmpty())
                .map(Role::new).forEach(roleRepo::save);
    }
}
