package com.phongngohong08.bookingroom.repositories;

import com.phongngohong08.bookingroom.entities.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, String> {

    Boolean existsByName(String name);
}
