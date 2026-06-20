package com.productions.banking.admin.service;

import com.productions.banking.admin.dto.AdminUserDetailResponse;
import com.productions.banking.admin.dto.AdminUserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminService {

    Page<AdminUserResponse> getAllUsers(
            Pageable pageable);

    AdminUserDetailResponse getUserById(Long userId);

    void lockUser(Long userId);

    void unlockUser(Long userId);

    void grantAdmin(Long userId);

    void revokeAdmin(Long userId);
}