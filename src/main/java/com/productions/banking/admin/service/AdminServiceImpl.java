package com.productions.banking.admin.service;

import com.productions.banking.admin.dto.AdminUserResponse;
import com.productions.banking.user.entity.User;
import com.productions.banking.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
}