package com.productions.banking.account.service;

import com.productions.banking.account.entity.AccountSequence;
import com.productions.banking.account.repository.AccountSequenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AccountNumberGeneratorServiceImpl
        implements AccountNumberGeneratorService {

    private final AccountSequenceRepository repository;

    @Override
    @Transactional
    public String generate() {

        AccountSequence sequence =
                repository.lockById(1L);

        Long value = sequence.getNextValue();

        sequence.setNextValue(value + 1);

        return String.valueOf(value);
    }
}