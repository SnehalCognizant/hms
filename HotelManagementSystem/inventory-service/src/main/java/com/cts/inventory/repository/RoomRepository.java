package com.cts.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cts.inventory.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {}
