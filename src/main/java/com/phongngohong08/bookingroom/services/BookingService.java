package com.phongngohong08.bookingroom.services;

import com.phongngohong08.bookingroom.dtos.request.BookingRequest;
import com.phongngohong08.bookingroom.dtos.response.BookingResponse;
import com.phongngohong08.bookingroom.dtos.response.UserResponse;

import java.util.List;

public interface BookingService {

    BookingResponse createBooking(String roomId, String userId, BookingRequest bookingRequest);

    BookingResponse findBookingById(String bookingId);

    List<BookingResponse> getAllBookings();

    BookingResponse cancelBooking(String bookingId);

    List<BookingResponse> getUserBookingHistory(String userId);
}
