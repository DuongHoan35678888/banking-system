package com.productions.banking.account.dto;

import java.math.BigDecimal;

public record DepositResponse(

        String accountNumber,

        BigDecimal oldBalance,

        BigDecimal newBalance
) {
}
