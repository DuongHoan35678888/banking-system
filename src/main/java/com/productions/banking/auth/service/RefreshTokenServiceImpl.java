package com.productions.banking.auth.service;

import com.productions.banking.auth.entity.RefreshToken;
import com.productions.banking.auth.repository.RefreshTokenRepository;
import com.productions.banking.common.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository repository;

    private static final long REFRESH_EXPIRATION =
            1000L * 60 * 60 * 24 * 7; // 7 days

    @Override
    public RefreshToken createRefreshToken(String username) {

        RefreshToken token = new RefreshToken();
        token.setUsername(username);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(Instant.now().plusMillis(REFRESH_EXPIRATION));

        return repository.save(token);
    }

    @Override
    public RefreshToken verify(String token) {

        RefreshToken refreshToken = repository.findByToken(token)
                .orElseThrow(() ->
                        new BadRequestException("Invalid refresh token"));

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            repository.delete(refreshToken);
            throw new BadRequestException("Refresh token expired");
        }

        return refreshToken;
    }

    @Override
    public void deleteByUsername(String username) {
        repository.deleteByUsername(username);
    }
}