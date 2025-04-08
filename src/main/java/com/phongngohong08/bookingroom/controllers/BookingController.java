package com.phongngohong08.bookingroom.controllers;

import com.phongngohong08.bookingroom.dtos.request.BookingRequest;
import com.phongngohong08.bookingroom.dtos.response.ApiResponse;
import com.phongngohong08.bookingroom.dtos.response.BookingResponse;
import com.phongngohong08.bookingroom.services.BookingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingController {

    BookingService bookingService;

    @PostMapping("/{roomId}/{userId}")
    public ApiResponse<BookingResponse> createBooking(
            @PathVariable String roomId,
            @PathVariable String userId,
            @RequestBody BookingRequest bookingRequest) {

        BookingResponse bookingResponse = bookingService.createBooking(roomId, userId, bookingRequest);
        return ApiResponse.<BookingResponse>builder().result(bookingResponse).build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('admin')")
    public ApiResponse<List<BookingResponse>> getAllBookings() {
        List<BookingResponse> bookingResponses = bookingService.getAllBookings();
        return ApiResponse.<List<BookingResponse>>builder().result(bookingResponses).build();
    }

    @GetMapping("/{id}")
    public ApiResponse<BookingResponse> getBookingById(@PathVariable String id) {
        BookingResponse bookingResponse = bookingService.findBookingById(id);
        return ApiResponse.<BookingResponse>builder().result(bookingResponse).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('admin')")
    public ApiResponse<BookingResponse> cancelBooking(@PathVariable String id) {
        BookingResponse bookingResponse = bookingService.cancelBooking(id);
        return ApiResponse.<BookingResponse>builder().result(bookingResponse).build();
    }

    @GetMapping("/users/{userId}")
    public ApiResponse<List<BookingResponse>> getUserBookingHistory(@PathVariable String userId) {
        List<BookingResponse> bookingResponses = bookingService.getUserBookingHistory(userId);
        return ApiResponse.<List<BookingResponse>>builder().result(bookingResponses).build();
    }
}
