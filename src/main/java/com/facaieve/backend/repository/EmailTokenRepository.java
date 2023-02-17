package com.facaieve.backend.repository;

import com.facaieve.backend.entity.email.EmailTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmailTokenRepository extends JpaRepository<EmailTokenEntity, Long> {
    Optional<EmailTokenEntity> findByTokenAndExpiryDateAfterAndExpired(String token, LocalDateTime now, boolean expired);
    Optional<EmailTokenEntity> findByUserEmailAndExpiryDateAfterAndExpired(String userEmail, LocalDateTime now, boolean expired);
}
