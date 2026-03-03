package com.travelease.guest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.travelease.guest.entity.ServiceRequest;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {}
