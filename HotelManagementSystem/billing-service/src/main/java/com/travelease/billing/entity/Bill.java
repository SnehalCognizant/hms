package com.travelease.billing.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billId;

    private Long reservationId;
    private Long userId;
    private Double amount;
    private LocalDate billDate;
    private String status; // e.g., "PENDING", "PAID", "CANCELLED"
}
