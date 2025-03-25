package com.phongngohong08.bookingroom.services;

import com.phongngohong08.bookingroom.dtos.request.RoomRequest;
import com.phongngohong08.bookingroom.dtos.response.RoomResponse;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public interface RoomService {

    RoomResponse createRoom(MultipartFile photo, RoomRequest roomRequest);

    List<String> getAllRoomTypes();

    String createRoomType(String roomType);

    List<RoomResponse> getAllRooms();

    RoomResponse deleteRoom(String roomId);

    RoomResponse updateRoom(String roomId, RoomRequest roomRequest, MultipartFile photo);

    RoomResponse getRoomById(String roomId);

    List<RoomResponse> getAvailableRoomsByDataAndType(LocalDate checkinDate, LocalDate checkoutDate, String roomType);

    List<RoomResponse> getAllAvailableRooms();
}
