package com.dailycodework.dream_shop.service.user;

import com.dailycodework.dream_shop.exception.AlreadyExistException;
import com.dailycodework.dream_shop.exception.ResourceNotFoundException;
import com.dailycodework.dream_shop.model.User;
import com.dailycodework.dream_shop.repository.UserRepo;
import com.dailycodework.dream_shop.request.CreateUserRequest;
import com.dailycodework.dream_shop.request.UserUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User getUserById(Long userId) throws ResourceNotFoundException {
        return userRepo.findById(userId).orElseThrow(
                ()-> new ResourceNotFoundException("User not found"));
    }

    @Override
    public User createUser(CreateUserRequest req) throws AlreadyExistException {
        return Optional.of(req)
                .filter(user -> !userRepo.existsByEmail(user.getEmail()))
                .map(request -> {
                    User user = new User();
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    user.setEmail(request.getEmail());
                    user.setPassword(request.getPassword());
                    return userRepo.save(user);
                }).orElseThrow(()-> new AlreadyExistException("User already exists"));
    }

    @Override
    public User updateUser(UserUpdateRequest req, Long userId) throws ResourceNotFoundException {
        return  userRepo.findById(userId).map(existingUser ->{
            existingUser.setFirstName(req.getFirstName());
            existingUser.setLastName(req.getLastName());
            return userRepo.save(existingUser);
        }).orElseThrow(()-> new ResourceNotFoundException("User not found"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepo.findById(userId).ifPresentOrElse(userRepo::delete,
                () -> new ResourceNotFoundException("User not found"));
    }
}
