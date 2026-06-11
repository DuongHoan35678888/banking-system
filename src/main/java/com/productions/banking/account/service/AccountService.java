package com.productions.banking.account.service;

import com.productions.banking.account.dto.AccountResponse;
import com.productions.banking.account.dto.CreateAccountRequest;
import com.productions.banking.account.dto.DepositRequest;
import com.productions.banking.account.dto.DepositResponse;

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

}