package com.dailycodework.dream_shop.data;

import com.dailycodework.dream_shop.model.User;
import com.dailycodework.dream_shop.repository.UserRepo;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements ApplicationListener {

    private final UserRepo userRepo;

    public DataInitializer(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        createDefaultUserIfNotExists();
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
            user.setPassword("password");
            userRepo.save(user);
            System.out.println("Default vet user " + i + " created");

        }
    }
}
