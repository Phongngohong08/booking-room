package com.phongngohong08.bookingroom.mapper;

import com.phongngohong08.bookingroom.dtos.request.BookingRequest;
import com.phongngohong08.bookingroom.dtos.response.BookingResponse;
import com.phongngohong08.bookingroom.entities.Booking;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    Booking toBooking(BookingRequest request);

    BookingResponse toBookingResponse(Booking booking);
}
