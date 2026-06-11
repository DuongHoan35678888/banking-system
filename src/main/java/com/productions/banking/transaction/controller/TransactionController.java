package com.productions.banking.transaction.controller;

import com.productions.banking.transaction.dto.TransactionResponse;
import com.productions.banking.transaction.dto.TransferRequest;
import com.productions.banking.transaction.dto.TransferResponse;
import com.productions.banking.transaction.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transfers")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransferResponse> transfer(
            Authentication authentication,
            @Valid @RequestBody TransferRequest request) {

        return ResponseEntity.ok(
                transactionService.transfer(
                        authentication.getName(),
                        request
                )
        );
    }

    @GetMapping("/my-history")
    public ResponseEntity<Page<TransactionResponse>>
    getMyHistory(
            Authentication authentication,
            @PageableDefault(
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            )
            Pageable pageable) {

        return ResponseEntity.ok(
                transactionService.getMyHistory(
                        authentication.getName(),
                        pageable
                )
        );
    }
}