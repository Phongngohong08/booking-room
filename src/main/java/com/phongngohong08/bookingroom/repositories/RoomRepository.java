package com.phongngohong08.bookingroom.repositories;

import com.phongngohong08.bookingroom.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {

    @Query("SELECT r FROM rooms r WHERE r.roomType = :roomType AND r.id NOT IN " +
            "(SELECT b.room.id FROM bookings b WHERE " +
            "(b.checkinDate <= :checkoutDate AND b.checkoutDate >= :checkinDate))")
    List<Room> findAvailableRoomsByDatesAndTypes(@Param("checkinDate") LocalDate checkinDate,
                                                 @Param("checkoutDate") LocalDate checkoutDate,
                                                 @Param("roomType") String roomType);


    @Query("SELECT r FROM rooms r WHERE r.id NOT IN (SELECT b.room.id FROM bookings b WHERE b.status IN ('cancelled', 'checked_out'))")
    List<Room> getAllAvailableRooms();
}
