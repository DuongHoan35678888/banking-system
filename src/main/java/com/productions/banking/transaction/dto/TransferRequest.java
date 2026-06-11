package com.productions.banking.transaction.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransferRequest(

        @NotNull
        Long sourceAccountId,

        @NotBlank
        String toAccountNumber,

        @DecimalMin("0.01")
        BigDecimal amount
) {
}
