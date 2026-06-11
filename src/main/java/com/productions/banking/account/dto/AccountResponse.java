package com.productions.banking.account.dto;

import com.productions.banking.account.entity.AccountStatus;

import java.math.BigDecimal;

public record AccountResponse(

        Long id,

        String accountNumber,

        BigDecimal balance,

        AccountStatus status
) {
}