package com.cts.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cts.inventory.entity.RoomType;

public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {}
