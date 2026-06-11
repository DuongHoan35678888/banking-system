package com.productions.banking.account.controller;

import com.productions.banking.account.dto.AccountResponse;
import com.productions.banking.account.dto.CreateAccountRequest;
import com.productions.banking.account.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(
            Authentication authentication,
            @Valid @RequestBody CreateAccountRequest request) {

        AccountResponse response =
                accountService.createAccount(
                        authentication.getName(),
                        request
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-accounts")
    public ResponseEntity<List<AccountResponse>> getMyAccounts(
            Authentication authentication) {

        return ResponseEntity.ok(
                accountService.getMyAccounts(
                        authentication.getName()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccount(
            @PathVariable Long id,
            Authentication authentication) {

        return ResponseEntity.ok(
                accountService.getAccountById(
                        authentication.getName(),
                        id
                )
        );
    }
}