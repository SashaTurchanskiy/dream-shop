package com.dailycodework.dream_shop.service.user;

import com.dailycodework.dream_shop.dto.UserDto;
import com.dailycodework.dream_shop.exception.AlreadyExistException;
import com.dailycodework.dream_shop.exception.ResourceNotFoundException;
import com.dailycodework.dream_shop.model.User;
import com.dailycodework.dream_shop.request.CreateUserRequest;
import com.dailycodework.dream_shop.request.UserUpdateRequest;

public interface IUserService {
    User getUserById(Long userId) throws ResourceNotFoundException;
    User createUser(CreateUserRequest req) throws ResourceNotFoundException, AlreadyExistException;
    User updateUser(UserUpdateRequest req, Long userId) throws ResourceNotFoundException;
    void deleteUser(Long userId) throws ResourceNotFoundException;

    UserDto convertToDto(User user);

    User getAuthenticatedUser();
}
