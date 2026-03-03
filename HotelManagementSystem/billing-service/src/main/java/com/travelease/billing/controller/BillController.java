package com.travelease.billing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.travelease.billing.entity.Bill;
import com.travelease.billing.service.BillService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bills")
public class BillController {
    
    @Autowired
    private BillService billService;

    @PostMapping("/generate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Bill> generateBill(@RequestBody Bill bill) {
        Bill generatedBill = billService.generateBill(bill);
        return ResponseEntity.status(HttpStatus.CREATED).body(generatedBill);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Bill>> getAllBills() {
        List<Bill> bills = billService.getAllBills();
        return ResponseEntity.ok(bills);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getBill(@PathVariable("id") Long id) {
        Optional<Bill> bill = billService.getBillById(id);
        if (bill.isPresent()) {
            return ResponseEntity.ok(bill.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bill not found");
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Bill> updateBillStatus(@PathVariable("id") Long id, @RequestParam("status") String status) {
        Bill updatedBill = billService.updateBillStatus(id, status);
        return ResponseEntity.ok(updatedBill);
    }
}
