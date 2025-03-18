package com.phongngohong08.bookingroom.controllers;

import com.phongngohong08.bookingroom.dtos.request.UpdateUserRequest;
import com.phongngohong08.bookingroom.dtos.response.ApiResponse;
import com.phongngohong08.bookingroom.dtos.response.UserResponse;
import com.phongngohong08.bookingroom.services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

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
}
