package com.phongngohong08.bookingroom.repositories;

import com.phongngohong08.bookingroom.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
}
