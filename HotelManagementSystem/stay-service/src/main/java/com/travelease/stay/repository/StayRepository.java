package com.travelease.stay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.travelease.stay.entity.Stay;

public interface StayRepository extends JpaRepository<Stay, Long> {}
