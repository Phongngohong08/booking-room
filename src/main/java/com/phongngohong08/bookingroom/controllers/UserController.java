package com.phongngohong08.bookingroom.controllers;

import com.phongngohong08.bookingroom.dtos.request.UpdateUserRequest;
import com.phongngohong08.bookingroom.dtos.response.ApiResponse;
import com.phongngohong08.bookingroom.dtos.response.UserResponse;
import com.phongngohong08.bookingroom.services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @PutMapping("/")
    public ApiResponse<UserResponse> update(String userId, @RequestBody UpdateUserRequest request) {
        UserResponse userResponse = userService.updateUser(userId, request);
        return ApiResponse.<UserResponse>builder().result(
                userResponse
        ).build();
    }
}
