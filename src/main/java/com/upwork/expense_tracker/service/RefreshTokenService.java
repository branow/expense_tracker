package com.upwork.expense_tracker.service;

import com.upwork.expense_tracker.entity.RefreshToken;
import com.upwork.expense_tracker.exception.EntityNotFoundException;
import com.upwork.expense_tracker.exception.TokenRefreshException;
import com.upwork.expense_tracker.repository.RefreshTokenRepository;
import com.upwork.expense_tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository repository;
    private final UserRepository userRepository;

    @Value("${security.refresh-token.expiration-time.seconds}")
    private Long refreshTokenDurationSeconds;


    public RefreshToken getByToken(String token) {
        return repository.findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException(RefreshToken.class, "token", token));
    }

    public RefreshToken createRefreshToken(Integer userId) {
        RefreshToken refreshToken = repository.findByUserId(userId).orElse(new RefreshToken());

        refreshToken.setUser(userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "id", userId)));
        refreshToken.setExpiryDate(Instant.now().plusSeconds(refreshTokenDurationSeconds));
        refreshToken.setToken(UUID.randomUUID().toString());

        return repository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            repository.delete(token);
            throw new TokenRefreshException("Refresh token was expired.", token.getToken());
        }
        return token;
    }

    @Transactional
    public void deleteByUserId(Integer userId) {
        repository.deleteByUser(userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "id", userId)));
    }
}
