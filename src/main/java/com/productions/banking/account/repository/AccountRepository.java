package com.productions.banking.account.repository;

import com.productions.banking.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    @Query("""
       select a
       from Account a
       where a.id = :accountId
       and a.user.username = :username
       """)
    Optional<Account> findByIdAndUsername(
            Long accountId,
            String username);

    Optional<Account> findByAccountNumberAndUserId(
            String accountNumber,
            Long userId
    );
}
