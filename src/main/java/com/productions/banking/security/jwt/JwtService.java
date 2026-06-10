package com.productions.banking.security.jwt;

import com.productions.banking.user.entity.User;

public interface JwtService {

    String generateToken(User user);

    String extractUsername(String token);

    boolean isTokenValid(String token, String username);
}
