package com.pseudonym.audi.service;

import com.pseudonym.audi.dto.MailDto;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

//    @Value("${spring.mail.username}")
    private static String FROM_ADDRESS = "pseudo.audi@gmail.com";
    private final JavaMailSender mailSender;


    public void mailSend(MailDto mailDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());
//        생략 시 application.yml의 username을 기본값을 사용
        message.setFrom(MailService.FROM_ADDRESS);
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());

        System.out.println("message = " + message);
        System.out.println("mailDto = " + mailDto);
        System.out.println("FROM_ADDRESS = " + FROM_ADDRESS);
        System.out.println("mailSender = " + mailSender);

        mailSender.send(message);
    }
}
