package com.travelease.stay.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stayId;

    private Long reservationId;
    private Long userId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String status; // e.g., "ONGOING", "COMPLETED"
}
