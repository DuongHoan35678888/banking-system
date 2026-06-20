package com.productions.banking.account.dto;

import java.math.BigDecimal;

public record WithdrawResponse (

    String accountNumber,

    BigDecimal oldBalance,

    BigDecimal newBalance
) {
}
