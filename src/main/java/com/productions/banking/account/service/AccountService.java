package com.productions.banking.account.service;

import com.productions.banking.account.dto.AccountResponse;
import com.productions.banking.account.dto.CreateAccountRequest;

import java.util.List;

public interface AccountService {

    AccountResponse createAccount(
            String username,
            CreateAccountRequest request
    );

    List<AccountResponse> getMyAccounts(String username);
}