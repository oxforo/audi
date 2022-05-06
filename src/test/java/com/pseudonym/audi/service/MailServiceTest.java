package com.pseudonym.audi.service;

import com.pseudonym.audi.dto.MailDto;
import com.pseudonym.audi.repository.EmailAuthRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {

    @Mock
    private EmailAuthRepository emailAuthRepository;
    @Mock
    private JavaMailSender javaMailSender;
    private MailService mailService;

    @BeforeEach
    void setup() {
        this.mailService = new MailService(emailAuthRepository,javaMailSender);
    }


    @Test
    void sendMailAuth() {
        MailDto mailDto = new MailDto();
        mailDto.setId("user1");
        mailDto.setEmail("user1@test.com");
    }

    @Test
    void validMailAuth() {

    }

    @Test
    void sendMailOTP() {

    }
}