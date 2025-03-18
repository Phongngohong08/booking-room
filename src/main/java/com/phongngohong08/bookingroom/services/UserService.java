package com.phongngohong08.bookingroom.services;

import com.phongngohong08.bookingroom.constant.PredefinedRole;
import com.phongngohong08.bookingroom.dtos.request.RegisterRequest;
import com.phongngohong08.bookingroom.dtos.request.UpdateUserRequest;
import com.phongngohong08.bookingroom.dtos.response.UserResponse;
import com.phongngohong08.bookingroom.entities.Role;
import com.phongngohong08.bookingroom.entities.User;
import com.phongngohong08.bookingroom.mapper.UserMapper;
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
    UserMapper userMapper;

    public UserResponse createUser(RegisterRequest request) {

        User user = userMapper.toUser(request);

        HashSet<Role> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);

        user.setRoles(roles);

        user = userRepository.save(user);

        return userMapper.toUserResponse(user);
    }

    public UserResponse updateUser(String userId, UpdateUserRequest request) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found"));

        userMapper.updateUser(user, request);

        user = userRepository.save(user);

        return userMapper.toUserResponse(user);
    }

    public UserResponse getUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found"));
        return userMapper.toUserResponse(user);
    }
}
