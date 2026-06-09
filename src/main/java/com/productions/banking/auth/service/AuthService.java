package com.productions.banking.auth.service;

import com.productions.banking.auth.dto.LoginRequest;
import com.productions.banking.auth.dto.RegisterRequest;

public interface AuthService {

    void register(RegisterRequest request);

    void login(LoginRequest request);
}
