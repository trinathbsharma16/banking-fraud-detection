package com.example.banking.service;

import com.example.banking.model.Transaction;
import com.example.banking.model.User;
import com.example.banking.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final FraudDetectionService fraudDetectionService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public Transaction processTransaction(Transaction transaction, User user) {
        transaction.setUser(user);

        boolean isFraud = fraudDetectionService.isFraudulent(transaction);
        transaction.setFraudulent(isFraud);

        transactionRepository.save(transaction);

        kafkaTemplate.send("transaction-events",
                "Transaction ID: " + transaction.getId() + " Fraudulent: " + isFraud);

        return transaction;
    }
}
