package com.productions.banking.transaction.service;

import com.productions.banking.transaction.dto.AdminTransactionResponse;
import com.productions.banking.transaction.dto.TransactionResponse;
import com.productions.banking.transaction.dto.TransferRequest;
import com.productions.banking.transaction.dto.TransferResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionService {

    TransferResponse transfer(String username, TransferRequest transferRequest);

    Page<TransactionResponse> getMyHistory(
            String username,
            Pageable pageable);

    List<AdminTransactionResponse> getAllTransactions();
}
