package com.productions.banking.transaction.dto;

import java.math.BigDecimal;

public record TransferResponse(

        String fromAccountNumber,

        String toAccountNumber,

        BigDecimal amount,

        BigDecimal remainingBalance
) {
}
