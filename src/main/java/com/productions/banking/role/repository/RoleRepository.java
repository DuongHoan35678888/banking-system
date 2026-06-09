package com.productions.banking.role.repository;

import com.productions.banking.role.entity.Role;
import com.productions.banking.role.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository
        extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName name);
}
