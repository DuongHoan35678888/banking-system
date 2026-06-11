package com.productions.banking.account.service;

import com.productions.banking.account.dto.AccountResponse;
import com.productions.banking.account.dto.CreateAccountRequest;

public interface AccountService {

    AccountResponse createAccount(
            String username,
            CreateAccountRequest request
    );
}