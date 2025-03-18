package com.phongngohong08.bookingroom.services;

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
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
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
}
