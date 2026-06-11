package com.productions.banking.account.service;

import com.productions.banking.account.dto.AccountResponse;
import com.productions.banking.account.dto.CreateAccountRequest;
import com.productions.banking.account.entity.Account;
import com.productions.banking.account.entity.AccountStatus;
import com.productions.banking.account.repository.AccountRepository;
import com.productions.banking.common.exception.BadRequestException;
import com.productions.banking.user.entity.User;
import com.productions.banking.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountNumberGeneratorService accountNumberGenerator;

    @Override
    @Transactional
    public AccountResponse createAccount(
            String username,
            CreateAccountRequest request) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new BadRequestException("User not found"));

        String accountNumber =
                accountNumberGenerator.generate();

        Account account = Account.builder()
                .accountNumber(accountNumber)
                .balance(BigDecimal.ZERO)
                .status(AccountStatus.ACTIVE)
                .user(user)
                .build();

        Account savedAccount =
                accountRepository.save(account);

        return new AccountResponse(
                savedAccount.getId(),
                savedAccount.getAccountNumber(),
                savedAccount.getBalance(),
                savedAccount.getStatus()
        );
    }
}