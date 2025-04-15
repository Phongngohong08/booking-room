package com.phongngohong08.bookingroom.services.impl;

import com.phongngohong08.bookingroom.dtos.request.RoomRequest;
import com.phongngohong08.bookingroom.dtos.response.RoomResponse;
import com.phongngohong08.bookingroom.entities.Room;
import com.phongngohong08.bookingroom.entities.RoomType;
import com.phongngohong08.bookingroom.exception.AppException;
import com.phongngohong08.bookingroom.exception.ErrorCode;
import com.phongngohong08.bookingroom.mapper.RoomMapper;
import com.phongngohong08.bookingroom.repositories.RoomRepository;
import com.phongngohong08.bookingroom.repositories.RoomTypeRepository;
import com.phongngohong08.bookingroom.services.AwsS3Service;
import com.phongngohong08.bookingroom.services.RoomService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoomServiceImpl implements RoomService {

    RoomTypeRepository roomTypeRepository;
    RoomRepository roomRepository;
    AwsS3Service awsS3Service;
    RoomMapper roomMapper;

    @Override
    public RoomResponse createRoom(MultipartFile photo, RoomRequest roomRequest) {

        Room room = roomMapper.toRoom(roomRequest);

        String imageUrl = awsS3Service.saveImageToS3(photo);
        room.setPhotoUrl(imageUrl);

        Boolean isRoomTypeExist = roomTypeRepository.existsByName(roomRequest.getRoomType());
        if (!isRoomTypeExist) {
            RoomType roomType = new RoomType();
            roomType.setName(roomRequest.getRoomType());
            roomTypeRepository.save(roomType);
        }

        Room savedRoom = roomRepository.save(room);

        return roomMapper.toRoomResponse(savedRoom);
    }

    @Override
    public List<String> getAllRoomTypes() {
        return roomTypeRepository.findAll()
                .stream()
                .map(RoomType::getName)
                .collect(Collectors.toList());
    }

    @Override
    public String createRoomType(String name) {
        RoomType newRoomType = new RoomType();
        newRoomType.setName(name);
        roomTypeRepository.save(newRoomType);
        return name;
    }

    @Override
    public List<RoomResponse> getAllRooms() {

        List<Room> rooms = roomRepository.findAll();
        List<RoomResponse> roomResponses = new ArrayList<>();
        for (Room room : rooms) {
            RoomResponse roomResponse = roomMapper.toRoomResponse(room);
            roomResponses.add(roomResponse);
        }
        return roomResponses;
    }

    @Override
    public RoomResponse deleteRoom(String roomId) {
        roomRepository.findById(roomId)
                .ifPresent(roomRepository::delete);
        return null;
    }

    @Override
    public RoomResponse updateRoom(String roomId, RoomRequest roomRequest, MultipartFile photo) {

        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new AppException(ErrorCode.ROOM_NOT_EXISTED)
        );

        room.setName(roomRequest.getName());
        room.setDescription(roomRequest.getDescription());
        room.setPricePerDay(roomRequest.getPricePerDay());
        room.setCapacity(roomRequest.getCapacity());
        room.setRoomType(roomRequest.getRoomType());

        if (photo != null) {
            String imageUrl = awsS3Service.saveImageToS3(photo);
            room.setPhotoUrl(imageUrl);
        }

        Room savedRoom = roomRepository.save(room);
        return roomMapper.toRoomResponse(savedRoom);
    }

    @Override
    public RoomResponse getRoomById(String roomId) {

        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new AppException(ErrorCode.ROOM_NOT_EXISTED)
        );

        return roomMapper.toRoomResponse(room);
    }

    @Override
    public List<RoomResponse> getAvailableRoomsByDataAndType(LocalDate checkinDate, LocalDate checkoutDate, String roomType) {

        List<Room> rooms = roomRepository.findAvailableRoomsByDatesAndTypes(checkinDate, checkoutDate, roomType);
        return rooms.stream()
                .map(roomMapper::toRoomResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomResponse> getAllAvailableRooms() {

        List<Room> rooms = roomRepository.getAllAvailableRooms();
        return rooms.stream()
                .map(roomMapper::toRoomResponse)
                .collect(Collectors.toList());
    }
}
