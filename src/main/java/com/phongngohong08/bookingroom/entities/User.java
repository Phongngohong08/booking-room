package com.phongngohong08.bookingroom.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "email", unique = true)
    String email;

    @Column(name = "hashed_password")
    String hashedPassword;
    String username;
    LocalDate dob;

    @ManyToMany
    Set<Role> roles;
}
