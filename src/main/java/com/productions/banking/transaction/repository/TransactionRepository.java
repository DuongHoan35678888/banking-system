package com.productions.banking.transaction.repository;

import com.productions.banking.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {

    List<Transaction> findByFromAccountNumberInOrToAccountNumberInOrderByCreatedAtDesc(
            List<String> fromAccounts,
            List<String> toAccounts
    );
}
