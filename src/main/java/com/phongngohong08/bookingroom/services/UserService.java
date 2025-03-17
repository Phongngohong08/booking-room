package com.phongngohong08.bookingroom.services;

import com.phongngohong08.bookingroom.constant.PredefinedRole;
import com.phongngohong08.bookingroom.dtos.request.RegisterRequest;
import com.phongngohong08.bookingroom.dtos.request.UpdateUserRequest;
import com.phongngohong08.bookingroom.dtos.response.UserResponse;
import com.phongngohong08.bookingroom.entities.Role;
import com.phongngohong08.bookingroom.entities.User;
import com.phongngohong08.bookingroom.repositories.RoleRepository;
import com.phongngohong08.bookingroom.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    RoleRepository roleRepository;

    public UserResponse createUser(RegisterRequest request) {

        HashSet<Role> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);

        User user = User.builder()
                .email(request.getEmail())
                .dob(request.getDob())
                .username(request.getUsername())
                .hashedPassword(request.getPassword())
                .roles(roles)
                .build();

        user = userRepository.save(user);

        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .dob(user.getDob())
                .roles(new HashSet<>())
                .build();
    }

    public UserResponse updateUser(String userId, UpdateUserRequest request) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found"));

        user.setDob(request.getDob());
        user.setUsername(request.getUsername());
        user.setHashedPassword(request.getPassword());

        user = userRepository.save(user);

        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .dob(user.getDob())
                .roles(new HashSet<>())
                .build();
    }

    public UserResponse getUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found"));
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .dob(user.getDob())
                .roles(new HashSet<>())
                .build();
    }
}
