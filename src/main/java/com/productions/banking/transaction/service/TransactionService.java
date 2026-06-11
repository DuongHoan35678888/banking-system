package com.productions.banking.transaction.service;

import com.productions.banking.admin.dto.AdminTransactionResponse;
import com.productions.banking.transaction.dto.TransactionResponse;
import com.productions.banking.transaction.dto.TransferRequest;
import com.productions.banking.transaction.dto.TransferResponse;
import com.productions.banking.transaction.entity.TransactionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService {

    TransferResponse transfer(String username, TransferRequest transferRequest);

    Page<TransactionResponse> getMyHistory(
            String username,
            Pageable pageable);

    Page<AdminTransactionResponse> getAllTransactions(
            TransactionStatus status,
            Pageable pageable);
}
