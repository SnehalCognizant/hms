package com.travelease.billing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.travelease.billing.entity.Bill;

public interface BillRepository extends JpaRepository<Bill, Long> {}
