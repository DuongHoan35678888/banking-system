package com.productions.banking.auth.service;

import com.productions.banking.auth.entity.RefreshToken;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(String username);

    RefreshToken verify(String token);

    void deleteByUsername(String username);
}
