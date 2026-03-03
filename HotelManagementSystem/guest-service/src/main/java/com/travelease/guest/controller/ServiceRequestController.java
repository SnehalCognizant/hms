package com.travelease.guest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.travelease.guest.entity.ServiceRequest;
import com.travelease.guest.service.ServiceRequestService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/requests")
public class ServiceRequestController {
    
    @Autowired
    private ServiceRequestService serviceRequestService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ServiceRequest> createRequest(@RequestBody ServiceRequest request) {
        ServiceRequest createdRequest = serviceRequestService.createRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ServiceRequest>> getAllRequests() {
        List<ServiceRequest> requests = serviceRequestService.getAllRequests();
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getRequest(@PathVariable("id") Long id) {
        Optional<ServiceRequest> request = serviceRequestService.getRequestById(id);
        if (request.isPresent()) {
            return ResponseEntity.ok(request.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request not found");
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServiceRequest> updateRequestStatus(@PathVariable("id") Long id, @RequestParam("status") String status) {
        ServiceRequest updatedRequest = serviceRequestService.updateRequestStatus(id, status);
        return ResponseEntity.ok(updatedRequest);
    }
}