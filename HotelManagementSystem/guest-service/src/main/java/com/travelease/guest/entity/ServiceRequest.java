package com.travelease.guest.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    private Long userId;
    private String requestType; // e.g., "Housekeeping", "Food", "Laundry"
    private String description;
    private String status; // e.g., "PENDING", "IN_PROGRESS", "COMPLETED"
}
