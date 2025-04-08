package com.phongngohong08.bookingroom.controllers;

import com.phongngohong08.bookingroom.dtos.request.UpdateUserRequest;
import com.phongngohong08.bookingroom.dtos.response.ApiResponse;
import com.phongngohong08.bookingroom.dtos.response.UserResponse;
import com.phongngohong08.bookingroom.services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @PutMapping("/{userId}")
    public ApiResponse<UserResponse> update(@PathVariable String userId, @RequestBody UpdateUserRequest request) {
        UserResponse userResponse = userService.updateUser(userId, request);
        return ApiResponse.<UserResponse>builder().result(
                userResponse
        ).build();
    }

    @GetMapping("/{userId}")
    public ApiResponse<UserResponse> getUserById(@PathVariable String userId) {
        UserResponse userResponse = userService.getUserById(userId);
        return ApiResponse.<UserResponse>builder().result(
                userResponse
        ).build();
    }

    @GetMapping
    @PreAuthorize("hasRole('admin')")
    public ApiResponse<List<UserResponse>> getAllUsers() {
        List<UserResponse> userResponses = userService.getAllUsers();
        return ApiResponse.<List<UserResponse>>builder().result(
                userResponses
        ).build();
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('admin')")
    public ApiResponse<UserResponse> deleteUser(@PathVariable String userId) {
        UserResponse userResponse = userService.deleteUser(userId);
        return ApiResponse.<UserResponse>builder().result(
                userResponse
        ).build();
    }

    @GetMapping("/my_info")
    public ApiResponse<UserResponse> getMyInfo() {
        UserResponse userResponse = userService.getMyInfo();
        return ApiResponse.<UserResponse>builder().result(
                userResponse
        ).build();
    }
}
