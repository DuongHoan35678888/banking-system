package com.productions.banking.admin.dto;

import java.time.LocalDateTime;

public record AdminUserResponse(

        Long id,

        String username,

        String email,

        LocalDateTime createdAt
) {
}