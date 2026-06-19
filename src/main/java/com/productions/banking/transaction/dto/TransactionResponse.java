package com.productions.banking.transaction.dto;

import com.productions.banking.transaction.entity.TransactionDirection;
import com.productions.banking.transaction.entity.TransactionStatus;
import com.productions.banking.transaction.entity.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(

        Long id,

        TransactionType type,

        TransactionStatus status,

        String accountNumber,

        String relatedAccountNumber,

        BigDecimal amount,

        TransactionDirection direction,

        BigDecimal balanceBefore,

        BigDecimal balanceAfter,

        LocalDateTime createdAt
) {
}