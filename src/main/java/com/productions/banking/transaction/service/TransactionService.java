package com.productions.banking.transaction.service;

import com.productions.banking.transaction.dto.TransferRequest;
import com.productions.banking.transaction.dto.TransferResponse;

public interface TransactionService {

    TransferResponse transfer(String username, TransferRequest transferRequest);

}
