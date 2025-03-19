package com.phongngohong08.bookingroom.controllers;

import com.nimbusds.jose.JOSEException;
import com.phongngohong08.bookingroom.dtos.request.*;
import com.phongngohong08.bookingroom.dtos.response.ApiResponse;
import com.phongngohong08.bookingroom.dtos.response.AuthResponse;
import com.phongngohong08.bookingroom.dtos.response.IntrospectResponse;
import com.phongngohong08.bookingroom.dtos.response.UserResponse;
import com.phongngohong08.bookingroom.services.AuthService;
import com.phongngohong08.bookingroom.services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    AuthService authService;
    UserService userService;

    @PostMapping("/login")
    ApiResponse<AuthResponse> login(@RequestBody LoginRequest request) {

        AuthResponse authResponse = authService.authenticate(request);
        return ApiResponse.<AuthResponse>builder().result(
                authResponse
        ).build();
    }

    @PostMapping("/register")
    ApiResponse<UserResponse> register(@RequestBody RegisterRequest request) {

        UserResponse userResponse = userService.createUser(request);
        return ApiResponse.<UserResponse>builder().result(
                userResponse
        ).build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {

        IntrospectResponse introspectResponse = authService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder().result(
                introspectResponse
        ).build();
    }

    @PostMapping("/logout")
    ApiResponse<AuthResponse> logout(@RequestBody LogoutRequest request)
            throws ParseException, JOSEException {
        authService.logout(request);
        return ApiResponse.<AuthResponse>builder().build();
    }

    @PostMapping("/refresh_token")
    ApiResponse<AuthResponse> refreshToken(@RequestBody RefreshRequest request)
            throws ParseException, JOSEException {
        AuthResponse authResponse = authService.refreshToken(request);
        return ApiResponse.<AuthResponse>builder().result(
                authResponse
        ).build();
    }
}
