package com.pseudonym.audi.repository;

import com.pseudonym.audi.domain.EmailAuth;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.exceptions.ExceptionIncludingMockitoWarnings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class EmailAuthRepositoryTest {

    @Autowired
    EmailAuthRepository emailAuthRepository;

    @AfterEach
    public void cleanAll() { emailAuthRepository.deleteAll();}

    @Test
    void save_OK() {
        //given
        String id = "user1";
        String email = "user1@test.com";
        String autoToken = "123456";
        EmailAuth emailAuth = new EmailAuth(id,email,autoToken);

        //where
        EmailAuth savedEmailAuth = emailAuthRepository.save(emailAuth);

        //then
        assertEquals(emailAuth, savedEmailAuth);
    }

    @Test
    void findByIdAndAuthTokenAndExpireDateGreaterThanEqual_OK() {
        //given
        String id = "user1";
        String email = "user1@test.com";
        String autoToken = "123456";
        EmailAuth emailAuth = new EmailAuth(id,email,autoToken);
        emailAuthRepository.save(emailAuth);

        //where
        EmailAuth findEmailAuth = emailAuthRepository.findByIdAndAuthTokenAndExpireDateGreaterThanEqual(id
                ,autoToken, LocalDateTime.now()).orElseThrow();

        //then
        assertEquals(emailAuth,findEmailAuth);
    }

    @Test
    void findByIdAndAuthTokenAndExpireDateGreaterThanEqual_시간초과_FAIL() {
        //given
        String id = "user1";
        String email = "user1@test.com";
        String autoToken = "123456";
        EmailAuth emailAuth = new EmailAuth(id,email,autoToken);
        emailAuthRepository.save(emailAuth);

        //where, then
        assertThatThrownBy(() -> emailAuthRepository.findByIdAndAuthTokenAndExpireDateGreaterThanEqual(id
                ,autoToken
                , LocalDateTime.now().withNano(0).plusMinutes(100L)).orElseThrow())
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void findByIdAndEmail_OK() {
        //given
        String id = "user1";
        String email = "user1@test.com";
        String autoToken = "123456";
        EmailAuth emailAuth = new EmailAuth(id,email,autoToken);
        emailAuthRepository.save(emailAuth);

        //where
        EmailAuth findEmailAuth = emailAuthRepository.findByIdAndEmail(id,email).orElseThrow();

        //then
        assertEquals(emailAuth, findEmailAuth);
    }

    @Test
    void findByIdAndEmail_FAIL() {
        //given
        String id = "user1";
        String email = "user1@test.com";
        String autoToken = "123456";
        EmailAuth emailAuth = new EmailAuth(id,email,autoToken);
        emailAuthRepository.save(emailAuth);

        //where, then
        assertThatThrownBy(() ->emailAuthRepository.findByIdAndEmail("user2",email).orElseThrow())
                .isInstanceOf(RuntimeException.class);
    }
}