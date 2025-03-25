package com.phongngohong08.bookingroom.enums;

import lombok.Getter;

@Getter
public enum BookingStatus {
    PENDING("pending"),
    CONFIRMED("confirmed"),
    CANCELLED("cancelled"),
    CHECKED_IN("checked_in"),
    CHECKED_OUT("checked_out");

    private final String status;

    BookingStatus(String status) {
        this.status = status;
    }
}
