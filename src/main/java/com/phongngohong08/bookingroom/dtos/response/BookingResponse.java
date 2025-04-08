package com.phongngohong08.bookingroom.dtos.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingResponse {

    String id;
    String userId;
    String roomId;
    LocalDate checkinDate;
    LocalDate checkoutDate;
    String status;
    LocalDate createdAt;
    LocalDate updatedAt;
}
