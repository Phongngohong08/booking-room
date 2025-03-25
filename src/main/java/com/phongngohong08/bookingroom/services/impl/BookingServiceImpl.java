package com.phongngohong08.bookingroom.services.impl;

import com.phongngohong08.bookingroom.dtos.request.BookingRequest;
import com.phongngohong08.bookingroom.dtos.response.BookingResponse;
import com.phongngohong08.bookingroom.entities.Booking;
import com.phongngohong08.bookingroom.entities.Room;
import com.phongngohong08.bookingroom.entities.User;
import com.phongngohong08.bookingroom.enums.BookingStatus;
import com.phongngohong08.bookingroom.exception.AppException;
import com.phongngohong08.bookingroom.exception.ErrorCode;
import com.phongngohong08.bookingroom.mapper.BookingMapper;
import com.phongngohong08.bookingroom.repositories.BookingRepository;
import com.phongngohong08.bookingroom.repositories.RoomRepository;
import com.phongngohong08.bookingroom.repositories.UserRepository;
import com.phongngohong08.bookingroom.services.BookingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingServiceImpl implements BookingService {

    RoomRepository roomRepository;
    UserRepository userRepository;
    BookingRepository bookingRepository;
    BookingMapper bookingMapper;

    @Override
    public BookingResponse createBooking(String roomId, String userId, BookingRequest bookingRequest) {

        Booking booking = bookingMapper.toBooking(bookingRequest);

        if (booking.getCheckinDate().isAfter(booking.getCheckoutDate())) {
            throw new IllegalArgumentException("Check in date must come after check out date");
        }

        List<Booking> existingBookings = bookingRepository.findByRoomId(roomId);

        if (!roomIsAvailable(booking, existingBookings)) {
            throw new AppException(ErrorCode.ROOM_NOT_AVAILABLE);
        }

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_EXISTED));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        booking.setRoom(room);
        booking.setUser(user);
        booking.setPaymentId("UUID.randomUUID().toString()");
        booking.setStatus(BookingStatus.CONFIRMED.getStatus());
        bookingRepository.save(booking);

        BookingResponse response = bookingMapper.toBookingResponse(booking);
        response.setUserId(userId);
        response.setRoomId(roomId);
        return response;
    }

    @Override
    public BookingResponse findBookingById(String bookingId) {

        return bookingRepository.findById(bookingId)
                .map(bookingMapper::toBookingResponse)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKING_NOT_EXISTED));
    }

    @Override
    public List<BookingResponse> getAllBookings() {

        return bookingRepository.findAll()
                .stream()
                .map(bookingMapper::toBookingResponse)
                .toList();
    }

    @Override
    public BookingResponse cancelBooking(String bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKING_NOT_EXISTED));

        booking.setStatus(BookingStatus.CANCELLED.getStatus());
        return bookingMapper.toBookingResponse(booking);
    }

    @Override
    public List<BookingResponse> getUserBookingHistory(String userId) {

        return bookingRepository.findByUserId(userId)
                .stream()
                .map(bookingMapper::toBookingResponse)
                .toList();
    }

    private boolean roomIsAvailable(Booking bookingRequest, List<Booking> existingBookings) {
        return existingBookings.stream().noneMatch(existingBooking ->
                bookingRequest.getCheckinDate().isBefore(existingBooking.getCheckoutDate()) &&
                        bookingRequest.getCheckoutDate().isAfter(existingBooking.getCheckinDate())
        );
    }
}
