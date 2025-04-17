package com.dailycodework.dream_shop.service.user;

import com.dailycodework.dream_shop.dto.UserDto;
import com.dailycodework.dream_shop.exception.AlreadyExistException;
import com.dailycodework.dream_shop.exception.ResourceNotFoundException;
import com.dailycodework.dream_shop.model.Cart;
import com.dailycodework.dream_shop.model.User;
import com.dailycodework.dream_shop.repository.UserRepo;
import com.dailycodework.dream_shop.request.CreateUserRequest;
import com.dailycodework.dream_shop.request.UserUpdateRequest;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional
public class UserService implements IUserService {

    private final UserRepo userRepo;
    private final ModelMapper modelMapper;

    public UserService(UserRepo userRepo, ModelMapper modelMapper) {
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public User getUserById(Long userId) throws ResourceNotFoundException {
        return userRepo.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    @Override
    public User createUser(CreateUserRequest request) throws AlreadyExistException {
        return  Optional.of(request)
                .filter(user -> !userRepo.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setEmail(request.getEmail());
                    user.setPassword(request.getPassword());
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    return  userRepo.save(user);
                }) .orElseThrow(() -> new AlreadyExistException("Oops!" +request.getEmail() +" already exists!"));
    }

    @Override
    public User updateUser(UserUpdateRequest req, Long userId) throws ResourceNotFoundException {
        return userRepo.findById(userId)
                .map(existingUser -> {
                    existingUser.setFirstName(req.getFirstName());
                    existingUser.setLastName(req.getLastName());
                    return userRepo.save(existingUser);
                })
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepo.findById(userId).ifPresentOrElse(userRepo::delete,
                () -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    //added user confing
}
