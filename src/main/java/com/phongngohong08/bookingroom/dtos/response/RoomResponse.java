package com.phongngohong08.bookingroom.dtos.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomResponse {

    String id;

    String roomType;
    String createdByUserId;

    String name;
    String description;
    Long pricePerDay;
    Integer capacity;
    LocalDate createdAt;
    LocalDate updatedAt;
}
