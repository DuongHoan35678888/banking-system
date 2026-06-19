package com.productions.banking.config;

import com.productions.banking.account.entity.AccountSequence;
import com.productions.banking.account.repository.AccountSequenceRepository;
import com.productions.banking.role.entity.Role;
import com.productions.banking.role.entity.RoleName;
import com.productions.banking.role.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer
        implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final AccountSequenceRepository accountSequenceRepository;

    @Override
    public void run(String... args) {

        if (roleRepository.count() == 0) {

            roleRepository.save(
                    Role.builder()
                            .name(RoleName.ROLE_ADMIN)
                            .build());

            roleRepository.save(
                    Role.builder()
                            .name(RoleName.ROLE_CUSTOMER)
                            .build());

            roleRepository.save(
                    Role.builder()
                            .name(RoleName.ROLE_AUDITOR)
                            .build());
        }

        if (accountSequenceRepository.count() == 0) {

            accountSequenceRepository.save(
                    AccountSequence.builder()
                            .id(1L)
                            .nextValue(1000000000L)
                            .build()
            );
        }
    }
}
