package com.phongngohong08.bookingroom.services.impl;

import com.phongngohong08.bookingroom.constant.PredefinedRole;
import com.phongngohong08.bookingroom.dtos.request.RegisterRequest;
import com.phongngohong08.bookingroom.dtos.request.UpdateUserRequest;
import com.phongngohong08.bookingroom.dtos.response.UserResponse;
import com.phongngohong08.bookingroom.entities.Role;
import com.phongngohong08.bookingroom.entities.User;
import com.phongngohong08.bookingroom.exception.AppException;
import com.phongngohong08.bookingroom.exception.ErrorCode;
import com.phongngohong08.bookingroom.mapper.UserMapper;
import com.phongngohong08.bookingroom.repositories.RoleRepository;
import com.phongngohong08.bookingroom.repositories.UserRepository;
import com.phongngohong08.bookingroom.services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;

    public UserResponse createUser(RegisterRequest request) {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        User user = userMapper.toUser(request);
        user.setHashedPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<Role> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);

        user.setRoles(roles);

        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        return userMapper.toUserResponse(user);
    }

    public UserResponse updateUser(String userId, UpdateUserRequest request) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));

        userMapper.updateUser(user, request);

        user = userRepository.save(user);

        return userMapper.toUserResponse(user);
    }

    public UserResponse getUserById(String userId) {
        return userMapper.toUserResponse(userRepository.findById(userId).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    @Override
    public UserResponse deleteUser(String userId) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));

        UserResponse userResponse = userMapper.toUserResponse(user);
        userRepository.delete(user);

        return userResponse;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @Override
    public UserResponse getMyInfo() {

        var context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }
}
