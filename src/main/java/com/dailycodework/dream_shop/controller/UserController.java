package com.dailycodework.dream_shop.controller;

import com.dailycodework.dream_shop.dto.UserDto;
import com.dailycodework.dream_shop.exception.AlreadyExistException;
import com.dailycodework.dream_shop.exception.ResourceNotFoundException;
import com.dailycodework.dream_shop.model.User;
import com.dailycodework.dream_shop.request.CreateUserRequest;
import com.dailycodework.dream_shop.request.UserUpdateRequest;
import com.dailycodework.dream_shop.response.ApiResponse;
import com.dailycodework.dream_shop.service.user.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"${api.prefix}/users"})
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }
    @GetMapping("/{userId}/user")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) throws ResourceNotFoundException {
        try {
            User user = userService.getUserById(userId);
            UserDto userDto = userService.convertToDto(user);
            return ResponseEntity.ok(new ApiResponse("User found successfully", userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new ApiResponse("User not found", e.getMessage()));
        }
    }
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest req) throws ResourceNotFoundException {
        try {
            User createdUser = userService.createUser(req);
            UserDto userDto = userService.convertToDto(createdUser);
            return ResponseEntity.ok(new ApiResponse("User created successfully", userDto));
        } catch (ResourceNotFoundException | AlreadyExistException e) {
            return ResponseEntity.status(404).body(new ApiResponse("User not found", e.getMessage()));
        }
    }
    @PutMapping("/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UserUpdateRequest request, @PathVariable Long userId){
        try {
            User user = userService.updateUser(request, userId);
            UserDto userDto = userService.convertToDto(user);
            return ResponseEntity.ok(new ApiResponse("User updated successfully", userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new ApiResponse("User not found", e.getMessage()));
        }
    }
    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("User deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(new ApiResponse("User not found", e.getMessage()));
        }
    }
}