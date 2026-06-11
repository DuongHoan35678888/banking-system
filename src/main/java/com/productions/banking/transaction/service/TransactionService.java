package com.productions.banking.transaction.service;

import com.productions.banking.transaction.dto.TransactionResponse;
import com.productions.banking.transaction.dto.TransferRequest;
import com.productions.banking.transaction.dto.TransferResponse;

import java.util.List;

public interface TransactionService {

    TransferResponse transfer(String username, TransferRequest transferRequest);

    List<TransactionResponse> getMyHistory(
            String username);

}
