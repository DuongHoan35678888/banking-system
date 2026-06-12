package com.productions.banking.account.service;

import com.productions.banking.account.dto.*;

import java.util.List;

public interface AccountService {

    AccountResponse createAccount(
            String username,
            CreateAccountRequest request
    );

    List<AccountResponse> getMyAccounts(String username);

    AccountResponse getAccountById(
            String username,
            Long accountId
    );

    DepositResponse deposit(
            String username,
            DepositRequest request
    );

    AccountResponse withdraw(
            String username,
            WithdrawRequest request);
}