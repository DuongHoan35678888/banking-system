package com.productions.banking.admin.dto;

import java.time.LocalDateTime;
import java.util.Set;

public record AdminUserDetailResponse(

        Long id,

        String username,

        String email,

        Set<String> roles,

        LocalDateTime createdAt
) {
}