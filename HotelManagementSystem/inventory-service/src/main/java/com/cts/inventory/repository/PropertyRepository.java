package com.cts.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cts.inventory.entity.Property;

public interface PropertyRepository extends JpaRepository<Property, Long> {}
