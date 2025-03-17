package com.phongngohong08.bookingroom.services;

import com.phongngohong08.bookingroom.dtos.request.LoginRequest;
import com.phongngohong08.bookingroom.dtos.request.RegisterRequest;
import com.phongngohong08.bookingroom.dtos.response.UserResponse;
import com.phongngohong08.bookingroom.repositories.RoleRepository;
import com.phongngohong08.bookingroom.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {

    UserRepository userRepository;
    UserService userService;

    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return null;
        }
        return userService.createUser(request);
    }

    public UserResponse login(LoginRequest request) {
        return userRepository.findByEmail(request.getEmail()).map(user -> {
            if (user.getHashedPassword().equals(request.getPassword())) {
                return userService.getUserById(user.getId());
            }
            return null;
        }).orElse(null);
    }
}
