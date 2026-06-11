package com.productions.banking.transaction.dto;

import com.productions.banking.transaction.entity.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(

        Long id,

        String fromAccountNumber,

        String toAccountNumber,

        BigDecimal amount,

        TransactionStatus status,

        BigDecimal sourceBalanceBefore,

        BigDecimal sourceBalanceAfter,

        BigDecimal destinationBalanceBefore,

        BigDecimal destinationBalanceAfter,

        LocalDateTime createdAt
) {
}