package com.cts.inventory.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;
    private String roomNumber;

    @ManyToOne
    private Property property;

    @ManyToOne
    private RoomType roomType;
}
