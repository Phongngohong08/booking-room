package com.phongngohong08.bookingroom.mapper;

import com.phongngohong08.bookingroom.dtos.request.RoomRequest;
import com.phongngohong08.bookingroom.dtos.response.RoomResponse;
import com.phongngohong08.bookingroom.entities.Room;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    Room toRoom(RoomRequest request);

    RoomResponse toRoomResponse(Room room);
}
