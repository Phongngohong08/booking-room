package com.phongngohong08.bookingroom.repositories;

import com.phongngohong08.bookingroom.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {

    List<Booking> findByRoomId(String roomId);

    List<Booking> findByUserId(String userId);
}
