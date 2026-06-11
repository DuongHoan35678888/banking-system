package com.productions.banking.transaction.repository;

import com.productions.banking.transaction.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {

    Page<Transaction> findByFromAccountNumberInOrToAccountNumberIn(
            List<String> fromAccounts,
            List<String> toAccounts,
            Pageable pageable
    );
}
