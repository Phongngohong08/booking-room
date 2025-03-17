package com.phongngohong08.bookingroom.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginRequest {


    @NotBlank(message = "EMAIL_INVALID")
    @Size(min = 6, max = 50, message = "EMAIL_INVALID")
    String email;

    @NotBlank(message = "INVALID_PASSWORD")
    @Size(min = 6, max = 50, message = "EMAIL_INVALID")
    String password;
}
