package com.cts.inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.cts.inventory.entity.RoomType;
import com.cts.inventory.services.RoomTypeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/roomtypes")
public class RoomTypeController {
    @Autowired
    private RoomTypeService roomTypeService;

    @PostMapping("/add")
    public RoomType addRoomType(@RequestBody RoomType roomType) {
        return roomTypeService.addRoomType(roomType);
    }

    @GetMapping("/all")
    public List<RoomType> getAllRoomTypes() {
        return roomTypeService.getAllRoomTypes();
    }

    @GetMapping("/{id}")
    public Optional<RoomType> getRoomType(@PathVariable Long id) {
        return roomTypeService.getRoomTypeById(id);
    }
}
