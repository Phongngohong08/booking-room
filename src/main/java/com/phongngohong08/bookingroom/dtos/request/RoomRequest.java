package com.phongngohong08.bookingroom.dtos.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomRequest {

    String name;
    String description;
    Long pricePerDay;
    Integer capacity;
    String roomType;
}
