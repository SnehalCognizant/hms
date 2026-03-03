package com.cts.inventory.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cts.inventory.entity.RoomType;
import com.cts.inventory.repository.RoomTypeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoomTypeService {
    @Autowired
    private RoomTypeRepository roomTypeRepository;

    public RoomType addRoomType(RoomType roomType) {
        return roomTypeRepository.save(roomType);
    }

    public List<RoomType> getAllRoomTypes() {
        return roomTypeRepository.findAll();
    }

    public Optional<RoomType> getRoomTypeById(Long id) {
        return roomTypeRepository.findById(id);
    }
}
