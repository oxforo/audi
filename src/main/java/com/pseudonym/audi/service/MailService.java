package com.pseudonym.audi.service;

import com.pseudonym.audi.domain.EmailAuth;
import com.pseudonym.audi.dto.MailAuthDto;
import com.pseudonym.audi.dto.MailDto;
import com.pseudonym.audi.exception.CustomException;
import com.pseudonym.audi.exception.ErrorCode;
import com.pseudonym.audi.repository.EmailAuthRepository;
import com.pseudonym.audi.util.OTP.OTPTokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class MailService {

    @Value("${spring.mail.username}")
    private static String FROM_ADDRESS;
    private final EmailAuthRepository emailAuthRepository;
    private final JavaMailSender javaMailSender;

    @Async
    public boolean sendMailAuth(MailDto mailDto) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        String authKey = Integer.toString(ThreadLocalRandom.current().nextInt(100000, 1000000));

        simpleMailMessage.setFrom(FROM_ADDRESS);
        simpleMailMessage.setTo(mailDto.getEmail());
        simpleMailMessage.setSubject("[메일 인증] 가명처리시스템 AUDI 인증 코드");
        simpleMailMessage.setText("인증번호는 " + authKey + " 입니다.");
        saveMailAuth(mailDto, authKey);

        try {
            javaMailSender.send(simpleMailMessage);
        } catch (MailException e) {
            throw new CustomException(ErrorCode.MISS_EMAIL);
        }
        return true;
    }

    private void saveMailAuth(MailDto mailDto, String authKey){
        EmailAuth emailAuth = new EmailAuth(mailDto.getId()
                , mailDto.getEmail()
                , authKey);
        emailAuthRepository.save(emailAuth);
    }

    public boolean validMailAuth(MailAuthDto mailAuthDto){
        EmailAuth emailAuth = emailAuthRepository.findByIdAndAuthTokenAndExpireDateGreaterThanEqual(
                mailAuthDto.getId()
                , mailAuthDto.getAuthKey()
                , LocalDateTime.now())
            .orElseThrow(()-> new CustomException(ErrorCode.UNAUTHORIZED_AUKEY));

        emailAuth.useAuthToken();
        emailAuthRepository.save(emailAuth);
        return true;
    }

    public boolean sendMailOTP(MailDto mailDto) {

        String secretKey = OTPTokenGenerator.generateSecretKey();
        String barCodeUrl = OTPTokenGenerator.getGoogleOTPAuthURL(secretKey
                , mailDto.getId());
        saveOtpToken(mailDto, secretKey);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(FROM_ADDRESS);
        simpleMailMessage.setTo(mailDto.getEmail());
        simpleMailMessage.setSubject("[OTP 생성] 가명처리시스템 AUDI OTP QR 코드");
        simpleMailMessage.setText("OTP QR 코드 주소는 " + barCodeUrl + " 입니다.");

        try {
            javaMailSender.send(simpleMailMessage);
            return true;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.MISS_EMAIL);
        }
    }

    private void saveOtpToken(MailDto mailDto, String secretKey) {
        EmailAuth emailAuth = emailAuthRepository.findByIdAndEmail(mailDto.getId(), mailDto.getEmail())
                .orElseThrow(()->new CustomException(ErrorCode.UNMATCH_ID_AND_EMAIL));
        emailAuth.setOtpToken(secretKey);
        emailAuthRepository.save(emailAuth);
    }
}
