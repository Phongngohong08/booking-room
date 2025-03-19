package com.phongngohong08.bookingroom.services;

import com.phongngohong08.bookingroom.dtos.request.RegisterRequest;
import com.phongngohong08.bookingroom.dtos.request.UpdateUserRequest;
import com.phongngohong08.bookingroom.dtos.response.UserResponse;

public interface UserService {

    UserResponse createUser(RegisterRequest request);

    UserResponse updateUser(String userId, UpdateUserRequest request);

    UserResponse getUserById(String userId);
}
