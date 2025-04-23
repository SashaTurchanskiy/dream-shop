package com.dailycodework.dream_shop.data;

import com.dailycodework.dream_shop.model.Role;
import com.dailycodework.dream_shop.model.User;
import com.dailycodework.dream_shop.repository.UserRepo;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Transactional
@Component
public class DataInitializer implements ApplicationListener<ApplicationEvent> {

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
        Set<String> defaultRoles = Set.of("ROLE_ADMIN", "ROLE_USER");
        createDefaultUserIfNotExists();
        createDefaultRoleIfNotExists(defaultRoles);
        createDefaultAdminIfNotExists();
    }

    private void createDefaultUserIfNotExists() {
        Role userRole = roleRepo.findByName("ROLE_USER").get();
        for (int i = 0; i < 5; i++) {
            String defaultEmail = "user" + i + "@example.com";
            if (userRepo.existsByEmail(defaultEmail)) {
                continue;
            }
            User user = new User();
            user.setFirstName("User");
            user.setLastName("User" + i);
            user.setEmail(defaultEmail);
            user.setRoles(Set.of(userRole));
            user.setPassword(passwordEncoder.encode("123456"));
            userRepo.save(user);
            System.out.println("Default vet user " + i + " created");
        }
    }

    private void createDefaultAdminIfNotExists() {
        Role adminRole = roleRepo.findByName("ROLE_ADMIN").get();
        for (int i = 0; i < 2; i++) {
            String defaultEmail = "admin" + i + "@example.com";
            if (userRepo.existsByEmail(defaultEmail)) {
                continue;
            }
            User user = new User();
            user.setFirstName("Admin");
            user.setLastName("Admin" + i);
            user.setEmail(defaultEmail);
            user.setRoles(Set.of(adminRole));
            user.setPassword(passwordEncoder.encode("123456"));
            userRepo.save(user);
            System.out.println("Default admin user " + i + " created");
        }
    }




    private void createDefaultRoleIfNotExists(Set<String> roles) {
        roles.stream()
                .filter(role -> roleRepo.findByName(role).isEmpty())
                .map(Role::new).forEach(roleRepo::save);
    }
}
