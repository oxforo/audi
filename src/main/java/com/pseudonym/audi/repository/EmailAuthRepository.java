package com.pseudonym.audi.repository;

import com.pseudonym.audi.domain.EmailAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface EmailAuthRepository extends JpaRepository<EmailAuth, String> {
    EmailAuth save(EmailAuth emailAuth);
    Optional<EmailAuth> findByIdAndAuthTokenAndExpireDateGreaterThanEqual(String id,String authToken, LocalDateTime expireDate);
    Optional<EmailAuth> findByIdAndEmail(String id, String email);
}
