package com.travelease.guest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.travelease.guest.entity.ServiceRequest;
import com.travelease.guest.repository.ServiceRequestRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceRequestService {
    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    public ServiceRequest createRequest(ServiceRequest request) {
        request.setStatus("PENDING");
        return serviceRequestRepository.save(request);
    }

    public List<ServiceRequest> getAllRequests() {
        return serviceRequestRepository.findAll();
    }

    public Optional<ServiceRequest> getRequestById(Long id) {
        return serviceRequestRepository.findById(id);
    }

    public ServiceRequest updateRequestStatus(Long id, String status) {
        Optional<ServiceRequest> reqOpt = serviceRequestRepository.findById(id);
        if (reqOpt.isPresent()) {
            ServiceRequest request = reqOpt.get();
            request.setStatus(status);
            return serviceRequestRepository.save(request);
        }
        return null;
    }
}
