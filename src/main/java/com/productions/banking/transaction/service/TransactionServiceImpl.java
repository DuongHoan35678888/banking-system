package com.productions.banking.transaction.service;

import com.productions.banking.account.entity.Account;
import com.productions.banking.account.repository.AccountRepository;
import com.productions.banking.common.exception.BadRequestException;
import com.productions.banking.common.exception.ResourceNotFoundException;
import com.productions.banking.admin.dto.AdminTransactionResponse;
import com.productions.banking.transaction.dto.TransactionResponse;
import com.productions.banking.transaction.dto.TransferRequest;
import com.productions.banking.transaction.dto.TransferResponse;
import com.productions.banking.transaction.entity.Transaction;
import com.productions.banking.transaction.entity.TransactionStatus;
import com.productions.banking.transaction.repository.TransactionRepository;
import com.productions.banking.user.entity.User;
import com.productions.banking.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

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

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionResponse> getMyHistory(
            String username,
            Pageable pageable) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new BadRequestException("User not found"));

        List<Account> accounts =
                accountRepository.findByUserId(user.getId());

        List<String> accountNumbers = accounts.stream()
                .map(Account::getAccountNumber)
                .toList();

        if (accountNumbers.isEmpty()) {
            return Page.empty(pageable);
        }

        Page<Transaction> transactions =
                transactionRepository
                        .findByFromAccountNumberInOrToAccountNumberIn(
                                accountNumbers,
                                accountNumbers,
                                pageable
                        );

        return transactions.map(transaction ->
                new TransactionResponse(
                        transaction.getId(),
                        transaction.getFromAccountNumber(),
                        transaction.getToAccountNumber(),
                        transaction.getAmount(),
                        transaction.getStatus(),
                        transaction.getSourceBalanceBefore(),
                        transaction.getSourceBalanceAfter(),
                        transaction.getDestinationBalanceBefore(),
                        transaction.getDestinationBalanceAfter(),
                        transaction.getCreatedAt()
                )
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AdminTransactionResponse> getAllTransactions(
            Pageable pageable) {

        Page<Transaction> transactions =
                transactionRepository.findAll(pageable);

        return transactions.map(transaction ->
                new AdminTransactionResponse(
                        transaction.getId(),
                        transaction.getFromAccountNumber(),
                        transaction.getToAccountNumber(),
                        transaction.getAmount(),
                        transaction.getStatus(),
                        transaction.getCreatedAt()
                )
        );
    }
}