package com.travelease.stay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.travelease.stay.entity.Stay;
import com.travelease.stay.repository.StayRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StayService {
    @Autowired
    private StayRepository stayRepository;

    public Stay addStay(Stay stay) {
        return stayRepository.save(stay);
    }

    public List<Stay> getAllStays() {
        return stayRepository.findAll();
    }

    public Optional<Stay> getStayById(Long id) {
        return stayRepository.findById(id);
    }

    public Stay updateStayStatus(Long id, String status) {
        Optional<Stay> stayOpt = stayRepository.findById(id);
        if (stayOpt.isPresent()) {
            Stay stay = stayOpt.get();
            stay.setStatus(status);
            return stayRepository.save(stay);
        }
        return null;
    }
}
