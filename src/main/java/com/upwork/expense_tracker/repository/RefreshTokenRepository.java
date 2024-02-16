package com.upwork.expense_tracker.repository;

import com.upwork.expense_tracker.entity.RefreshToken;
import com.upwork.expense_tracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUserId(Integer userId);

    @Modifying
    void deleteByUser(User user);
}
