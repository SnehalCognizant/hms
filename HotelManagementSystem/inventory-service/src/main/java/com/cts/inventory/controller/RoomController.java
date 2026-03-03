package com.cts.inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.cts.inventory.entity.Room;
import com.cts.inventory.services.RoomService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    
    @Autowired
    private RoomService roomService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Room> addRoom(@RequestBody Room room) {
        Room addedRoom = roomService.addRoom(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedRoom);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getRoom(@PathVariable("id") Long id) {
        Optional<Room> room = roomService.getRoomById(id);
        if (room.isPresent()) {
            return ResponseEntity.ok(room.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found");
    }
    

@GetMapping("/_debug")
public Object debug(org.springframework.security.core.Authentication auth) {
    if (auth == null) return "NO AUTH PRESENT";

    return new Object() {
        public Object principal = auth.getPrincipal();
        public Object authorities = auth.getAuthorities();
    };
}

}
