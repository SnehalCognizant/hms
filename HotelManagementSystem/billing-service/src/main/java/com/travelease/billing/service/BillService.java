package com.travelease.billing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.travelease.billing.entity.Bill;
import com.travelease.billing.repository.BillRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BillService {
    @Autowired
    private BillRepository billRepository;

    public Bill generateBill(Bill bill) {
        bill.setStatus("PENDING");
        bill.setBillDate(java.time.LocalDate.now());
        return billRepository.save(bill);
    }

    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    public Optional<Bill> getBillById(Long id) {
        return billRepository.findById(id);
    }

    public Bill updateBillStatus(Long id, String status) {
        Optional<Bill> billOpt = billRepository.findById(id);
        if (billOpt.isPresent()) {
            Bill bill = billOpt.get();
            bill.setStatus(status);
            return billRepository.save(bill);
        }
        return null;
    }
}
