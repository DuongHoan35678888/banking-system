package com.productions.banking.admin.controller;

import com.productions.banking.transaction.dto.AdminTransactionResponse;
import com.productions.banking.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final TransactionService transactionService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/transactions")
    public List<AdminTransactionResponse> getAllTransactions() {

        return transactionService.getAllTransactions();
    }
}