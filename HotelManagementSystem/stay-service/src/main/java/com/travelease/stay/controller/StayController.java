package com.travelease.stay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.travelease.stay.entity.Stay;
import com.travelease.stay.service.StayService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/stays")
public class StayController {
    
    @Autowired
    private StayService stayService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Stay> addStay(@RequestBody Stay stay) {
        Stay addedStay = stayService.addStay(stay);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedStay);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<Stay>> getAllStays() {
        List<Stay> stays = stayService.getAllStays();
        return ResponseEntity.ok(stays);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getStay(@PathVariable("id") Long id) {
        Optional<Stay> stay = stayService.getStayById(id);
        if (stay.isPresent()) {
            return ResponseEntity.ok(stay.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Stay not found");
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Stay> updateStayStatus(@PathVariable("id") Long id, @RequestParam("status") String status) {
        Stay updatedStay = stayService.updateStayStatus(id, status);
        return ResponseEntity.ok(updatedStay);
    }
}
