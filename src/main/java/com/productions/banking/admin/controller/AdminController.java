package com.productions.banking.admin.controller;

import com.productions.banking.admin.dto.AdminTransactionResponse;
import com.productions.banking.admin.dto.AdminUserDetailResponse;
import com.productions.banking.admin.dto.AdminUserResponse;
import com.productions.banking.admin.service.AdminService;
import com.productions.banking.transaction.entity.TransactionStatus;
import com.productions.banking.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final TransactionService transactionService;
    private final AdminService adminService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/transactions")
    public ResponseEntity<Page<AdminTransactionResponse>>
    getAllTransactions(

            @RequestParam(required = false)
            TransactionStatus status,

            @PageableDefault(
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            )
            Pageable pageable) {

        return ResponseEntity.ok(
                transactionService.getAllTransactions(
                        status,
                        pageable
                )
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<Page<AdminUserResponse>>
    getAllUsers(

            @PageableDefault(
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            )
            Pageable pageable) {

        return ResponseEntity.ok(
                adminService.getAllUsers(
                        pageable
                )
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/{id}")
    public ResponseEntity<AdminUserDetailResponse>
    getUserById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                adminService.getUserById(id)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/{id}/lock")
    public ResponseEntity<Void> lockUser(
            @PathVariable Long id) {

        adminService.lockUser(id);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/{id}/unlock")
    public ResponseEntity<Void> unlockUser(
            @PathVariable Long id) {

        adminService.unlockUser(id);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/{id}/grant-admin")
    public ResponseEntity<Void> grantAdmin(
            @PathVariable Long id) {

        adminService.grantAdmin(id);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/{id}/revoke-admin")
    public ResponseEntity<Void> revokeAdmin(
            @PathVariable Long id) {

        adminService.revokeAdmin(id);

        return ResponseEntity.noContent().build();
    }
}