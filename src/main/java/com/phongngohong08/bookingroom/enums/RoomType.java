package com.phongngohong08.bookingroom.enums;

import lombok.Getter;

@Getter
public enum RoomType {
    SINGLE("Single"),
    DOUBLE("Double"),
    TRIPLE("Triple"),
    QUAD("Quad"),
    QUEEN("Queen"),
    KING("King"),
    TWIN("Twin"),
    DOUBLE_DOUBLE("Double Double"),
    STUDIO("Studio"),
    SUITE("Suite"),
    MASTER_SUITE("Master Suite"),
    JUNIOR_SUITE("Junior Suite");

    private final String value;

    RoomType(String value) {
        this.value = value;
    }
}
