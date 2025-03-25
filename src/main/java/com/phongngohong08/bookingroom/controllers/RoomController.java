package com.phongngohong08.bookingroom.controllers;

import com.phongngohong08.bookingroom.dtos.request.RoomRequest;
import com.phongngohong08.bookingroom.dtos.response.ApiResponse;
import com.phongngohong08.bookingroom.dtos.response.RoomResponse;
import com.phongngohong08.bookingroom.exception.AppException;
import com.phongngohong08.bookingroom.exception.ErrorCode;
import com.phongngohong08.bookingroom.services.RoomService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoomController {

    RoomService roomService;

    @PostMapping
    @PreAuthorize("hasRole('admin')")
    public ApiResponse<RoomResponse> createRoom(
            @RequestParam(value = "photo", required = false) MultipartFile photo,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "price_per_day", required = false) Long pricePerDay,
            @RequestParam(value = "capacity", required = false) Integer capacity,
            @RequestParam(value = "room_type", required = false) String roomType
    ) {
        if (photo == null || photo.isEmpty() || name == null || name.isBlank() || pricePerDay == null) {
            throw new AppException(ErrorCode.NOT_ENOUGH_INFORMATION);
        }
        if (pricePerDay <= 0) {
            throw new AppException(ErrorCode.INVALID_PRICE);
        }

        RoomRequest roomRequest = RoomRequest.builder()
                .name(name)
                .description(description)
                .pricePerDay(pricePerDay)
                .capacity(capacity)
                .roomType(roomType)
                .build();
        RoomResponse roomResponse = roomService.createRoom(photo, roomRequest);
        return ApiResponse.<RoomResponse>builder().result(roomResponse).build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ApiResponse<RoomResponse> updateRoom(
            @PathVariable String id,
            @RequestParam(value = "photo", required = false) MultipartFile photo,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "price_per_day", required = false) Long pricePerDay,
            @RequestParam(value = "capacity", required = false) Integer capacity,
            @RequestParam(value = "room_type", required = false) String roomType
    ) {
        if (photo == null || photo.isEmpty() || name == null || name.isBlank() || pricePerDay == null) {
            throw new AppException(ErrorCode.NOT_ENOUGH_INFORMATION);
        }
        RoomRequest roomRequest = RoomRequest.builder()
                .name(name)
                .description(description)
                .pricePerDay(pricePerDay)
                .capacity(capacity)
                .roomType(roomType)
                .build();
        RoomResponse roomResponse = roomService.updateRoom(id, roomRequest, photo);
        return ApiResponse.<RoomResponse>builder().result(roomResponse).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ApiResponse<RoomResponse> deleteRoom(@PathVariable String id) {
        RoomResponse roomResponse = roomService.deleteRoom(id);
        return ApiResponse.<RoomResponse>builder().result(roomResponse).build();
    }

    @GetMapping
    public ApiResponse<List<RoomResponse>> getAllRooms() {
        List<RoomResponse> roomResponses = roomService.getAllRooms();
        return ApiResponse.<List<RoomResponse>>builder().result(roomResponses).build();
    }

    @GetMapping("/{id}")
    public ApiResponse<RoomResponse> getRoomById(@PathVariable String id) {
        RoomResponse roomResponse = roomService.getRoomById(id);
        return ApiResponse.<RoomResponse>builder().result(roomResponse).build();
    }

    @GetMapping("/available_rooms_by_date_and_type")
    public ApiResponse<List<RoomResponse>> getAvailableRooms(
            @RequestParam(value = "checkin_date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam(value = "checkout_date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
            @RequestParam(value = "room_type", required = false) String roomType
    ) {

        List<RoomResponse> roomResponses = roomService.getAvailableRoomsByDataAndType(checkInDate, checkOutDate, roomType);
        return ApiResponse.<List<RoomResponse>>builder().result(roomResponses).build();
    }

    @GetMapping("/available_rooms")
    public ApiResponse<List<RoomResponse>> getAvailableRooms() {
        List<RoomResponse> roomResponses = roomService.getAllAvailableRooms();
        return ApiResponse.<List<RoomResponse>>builder().result(roomResponses).build();
    }

    @GetMapping("/room_types")
    public ApiResponse<List<String>> getAllRoomTypes() {
        List<String> roomTypes = roomService.getAllRoomTypes();
        return ApiResponse.<List<String>>builder().result(roomTypes).build();
    }

    @PostMapping("/room_types")
    @PreAuthorize("hasRole('admin')")
    public ApiResponse<String> createRoomType(@RequestParam(value = "room_type") String roomType) {
        String newRoomType = roomService.createRoomType(roomType);
        return ApiResponse.<String>builder().result(newRoomType).build();
    }
}
