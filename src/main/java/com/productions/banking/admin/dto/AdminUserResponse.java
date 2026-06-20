package com.productions.banking.admin.dto;

import com.productions.banking.user.entity.UserStatus;

import java.time.LocalDateTime;
import java.util.Set;

public record AdminUserResponse(

        Long id,

        String username,

        String email,

        Set<String> roles,

        UserStatus status,

        LocalDateTime createdAt
) {
}