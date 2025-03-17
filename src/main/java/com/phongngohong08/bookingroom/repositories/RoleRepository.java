package com.phongngohong08.bookingroom.repositories;

import com.phongngohong08.bookingroom.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
}
