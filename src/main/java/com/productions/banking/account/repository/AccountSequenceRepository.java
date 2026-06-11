package com.productions.banking.account.repository;

import com.productions.banking.account.entity.AccountSequence;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface AccountSequenceRepository
        extends JpaRepository<AccountSequence, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
           SELECT s
           FROM AccountSequence s
           WHERE s.id = :id
           """)
    AccountSequence lockById(@Param("id") Long id);
}