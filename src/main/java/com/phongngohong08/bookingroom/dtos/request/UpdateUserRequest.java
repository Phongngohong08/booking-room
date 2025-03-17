package com.phongngohong08.bookingroom.dtos.request;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserRequest {

    @Size(min = 6, max = 50, message = "INVALID_PASSWORD")
    String password;

    @Size(min = 6, max = 50)
    String username;

    LocalDate dob;
}
