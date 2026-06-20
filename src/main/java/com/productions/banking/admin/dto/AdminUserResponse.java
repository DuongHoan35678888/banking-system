package com.productions.banking.admin.dto;

import com.productions.banking.user.entity.UserStatus;

import java.time.LocalDateTime;

public record AdminUserResponse(

        Long id,

        String username,

        String email,

        UserStatus status,

        LocalDateTime createdAt
) {
}