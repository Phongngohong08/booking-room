package com.phongngohong08.bookingroom.mapping;

import com.phongngohong08.bookingroom.dtos.response.BookingResponse;
import com.phongngohong08.bookingroom.entities.Booking;

public class BookingMapping {

    public static BookingResponse toBookingResponse(Booking booking) {
        if ( booking == null ) {
            return null;
        }

        BookingResponse.BookingResponseBuilder bookingResponse = BookingResponse.builder();

        bookingResponse.id( booking.getId() );
        bookingResponse.checkinDate( booking.getCheckinDate() );
        bookingResponse.checkoutDate( booking.getCheckoutDate() );
        bookingResponse.status( booking.getStatus() );
        bookingResponse.createdAt( booking.getCreatedAt() );
        bookingResponse.updatedAt( booking.getUpdatedAt() );
        bookingResponse.roomId( booking.getRoom().getId() );
        bookingResponse.userId( booking.getUser().getId() );

        return bookingResponse.build();
    }
}
