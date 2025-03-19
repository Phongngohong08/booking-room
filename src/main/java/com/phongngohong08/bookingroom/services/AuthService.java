package com.phongngohong08.bookingroom.services;

import com.nimbusds.jose.JOSEException;
import com.phongngohong08.bookingroom.dtos.request.*;
import com.phongngohong08.bookingroom.dtos.response.AuthResponse;
import com.phongngohong08.bookingroom.dtos.response.IntrospectResponse;
import com.phongngohong08.bookingroom.dtos.response.UserResponse;

import java.text.ParseException;

public interface AuthService {

    AuthResponse authenticate(LoginRequest request);

    IntrospectResponse introspect(IntrospectRequest request)
            throws JOSEException, ParseException;

    void logout(LogoutRequest request) throws ParseException, JOSEException;

    AuthResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException;
}
