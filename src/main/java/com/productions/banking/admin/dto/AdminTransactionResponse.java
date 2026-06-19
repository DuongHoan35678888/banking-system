package com.productions.banking.admin.dto;

import com.productions.banking.transaction.entity.TransactionStatus;
import com.productions.banking.transaction.entity.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AdminTransactionResponse(

        Long id,

        TransactionType type,

        TransactionStatus status,

        String accountNumber,

        String relatedAccountNumber,

        BigDecimal amount,

        BigDecimal balanceBefore,

        BigDecimal balanceAfter,

        LocalDateTime createdAt
) {
}