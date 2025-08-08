package com.example.banking.service;

import com.example.banking.model.Transaction;
import org.springframework.stereotype.Service;

@Service
public class FraudDetectionService {

    public boolean isFraudulent(Transaction transaction) {
        // Simple rule-based fraud detection
        if (transaction.getAmount() > 100000) return true;

        if (transaction.getLocation() != null &&
            !transaction.getLocation().equalsIgnoreCase(transaction.getUser().getLocation())) {
            return true;
        }

        if (transaction.getTimestamp() != null &&
            transaction.getTimestamp().getHour() < 6) {
            return true;
        }

        return false;
    }
}
