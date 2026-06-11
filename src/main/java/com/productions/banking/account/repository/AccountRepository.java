package com.productions.banking.account.repository;

import com.productions.banking.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository
        extends JpaRepository<Account, Long> {

    List<Account> findByUserId(Long userId);

    Optional<Account> findByAccountNumber(String accountNumber);

    Optional<Account> findByIdAndUserId(
            Long accountId,
            Long userId
    );
}
