package com.productions.banking.admin.service;

import com.productions.banking.admin.dto.AdminUserDetailResponse;
import com.productions.banking.admin.dto.AdminUserResponse;
import com.productions.banking.common.exception.ResourceNotFoundException;
import com.productions.banking.role.entity.Role;
import com.productions.banking.user.entity.User;
import com.productions.banking.user.entity.UserStatus;
import com.productions.banking.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl
        implements AdminService {

    private final UserRepository userRepository;

    @Override
    public Page<AdminUserResponse> getAllUsers(
            Pageable pageable) {

        Page<User> users =
                userRepository.findAll(pageable);

        return users.map(user ->
                new AdminUserResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getCreatedAt()
                )
        );
    }

    @Override
    @Transactional(readOnly = true)
    public AdminUserDetailResponse getUserById(
            Long userId) {

        User user = userRepository
                .findWithRolesById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));

        Set<String> roles = user.getRoles()
                .stream()
                .map(Role::getName)
                .map(Enum::name)
                .collect(Collectors.toSet());

        return new AdminUserDetailResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                roles,
                user.getCreatedAt()
        );
    }

    @Override
    @Transactional
    public void lockUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));

        user.setStatus(UserStatus.BLOCKED);
    }

    @Override
    @Transactional
    public void unlockUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));

        user.setStatus(UserStatus.ACTIVE);
    }
}