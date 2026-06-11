package com.productions.banking.account.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record DepositRequest(

        @NotBlank
        String accountNumber,

        @DecimalMin("0.01")
        BigDecimal amount
) {
}
