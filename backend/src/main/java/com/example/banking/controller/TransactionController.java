package com.example.fintech.controller;

import com.example.fintech.model.TransactionRequest;
import com.example.fintech.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> processTransaction(@RequestBody TransactionRequest request) {
        boolean result = transactionService.processTransaction(request);
        if (result) {
            return ResponseEntity.ok("Transaction processed");
        } else {
            return ResponseEntity.badRequest().body("Fraudulent transaction detected");
        }
    }
}
