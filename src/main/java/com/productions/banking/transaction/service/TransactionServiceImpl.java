package com.productions.banking.transaction.service;

import com.productions.banking.account.entity.Account;
import com.productions.banking.account.repository.AccountRepository;
import com.productions.banking.common.exception.BadRequestException;
import com.productions.banking.common.exception.ResourceNotFoundException;
import com.productions.banking.transaction.dto.TransferRequest;
import com.productions.banking.transaction.dto.TransferResponse;
import com.productions.banking.transaction.entity.Transaction;
import com.productions.banking.transaction.entity.TransactionStatus;
import com.productions.banking.transaction.repository.TransactionRepository;
import com.productions.banking.user.entity.User;
import com.productions.banking.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl
        implements TransactionService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public TransferResponse transfer(
            String username,
            TransferRequest request) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new BadRequestException("User not found"));

        Account sourceAccount = accountRepository
                .findByIdAndUserId(
                        request.sourceAccountId(),
                        user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Source account not found"));

        Account destinationAccount = accountRepository
                .findByAccountNumber(
                        request.toAccountNumber())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Destination account not found"));

        if (sourceAccount.getId()
                .equals(destinationAccount.getId())) {

            throw new BadRequestException(
                    "Cannot transfer to same account");
        }

        // Lock theo thứ tự ID để tránh deadlock
        Long firstLockId = Math.min(
                sourceAccount.getId(),
                destinationAccount.getId());

        Long secondLockId = Math.max(
                sourceAccount.getId(),
                destinationAccount.getId());

        Account firstLocked = accountRepository
                .findByIdForUpdate(firstLockId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Account not found"));

        Account secondLocked = accountRepository
                .findByIdForUpdate(secondLockId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Account not found"));

        Account lockedSource =
                firstLocked.getId().equals(sourceAccount.getId())
                        ? firstLocked
                        : secondLocked;

        Account lockedDestination =
                firstLocked.getId().equals(destinationAccount.getId())
                        ? firstLocked
                        : secondLocked;

        if (!lockedSource.getUser().getId()
                .equals(user.getId())) {

            throw new AccessDeniedException(
                    "You do not own this account");
        }

        if (lockedSource.getBalance()
                .compareTo(request.amount()) < 0) {

            throw new BadRequestException(
                    "Insufficient balance");
        }

        BigDecimal sourceBalanceBefore =
                lockedSource.getBalance();

        BigDecimal destinationBalanceBefore =
                lockedDestination.getBalance();

        lockedSource.setBalance(
                sourceBalanceBefore.subtract(
                        request.amount()));

        lockedDestination.setBalance(
                destinationBalanceBefore.add(
                        request.amount()));

        BigDecimal sourceBalanceAfter =
                lockedSource.getBalance();

        BigDecimal destinationBalanceAfter =
                lockedDestination.getBalance();

        Transaction transaction =
                Transaction.builder()
                        .fromAccountNumber(
                                lockedSource.getAccountNumber())
                        .toAccountNumber(
                                lockedDestination.getAccountNumber())
                        .amount(request.amount())
                        .status(TransactionStatus.SUCCESS)
                        .sourceBalanceBefore(
                                sourceBalanceBefore)
                        .sourceBalanceAfter(
                                sourceBalanceAfter)
                        .destinationBalanceBefore(
                                destinationBalanceBefore)
                        .destinationBalanceAfter(
                                destinationBalanceAfter)
                        .build();

        transactionRepository.save(transaction);

        return new TransferResponse(
                lockedSource.getAccountNumber(),
                lockedDestination.getAccountNumber(),
                request.amount(),
                sourceBalanceAfter
        );
    }
}