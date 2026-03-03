package com.travelease.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.travelease.reservation.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {}
