package com.phongngohong08.bookingroom.controllers;

import com.phongngohong08.bookingroom.dtos.request.LoginRequest;
import com.phongngohong08.bookingroom.dtos.request.RegisterRequest;
import com.phongngohong08.bookingroom.dtos.response.ApiResponse;
import com.phongngohong08.bookingroom.dtos.response.AuthResponse;
import com.phongngohong08.bookingroom.dtos.response.UserResponse;
import com.phongngohong08.bookingroom.services.AuthService;
import com.phongngohong08.bookingroom.services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    AuthService authService;

    @PostMapping("/login")
    ApiResponse<AuthResponse> login(@RequestBody LoginRequest request) {

        UserResponse userResponse = authService.login(request);
        return ApiResponse.<AuthResponse>builder().result(
                AuthResponse.builder()
                        .token("token")
                        .authenticated(userResponse != null)
                        .build()
        ).build();
    }

    @PostMapping("/register")
    ApiResponse<UserResponse> register(@RequestBody RegisterRequest request) {

        UserResponse userResponse = authService.register(request);
        return ApiResponse.<UserResponse>builder().result(
                userResponse
        ).build();
    }
}
