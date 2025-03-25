package com.phongngohong08.bookingroom.dtos.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingRequest {

    @JsonAlias("checkin_date")
    LocalDate checkinDate;

    @JsonAlias("checkout_date")
    LocalDate checkoutDate;
}
