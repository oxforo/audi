package com.pseudonym.audi.service;

import com.pseudonym.audi.domain.EmailAuth;
import com.pseudonym.audi.domain.User;
import com.pseudonym.audi.dto.*;
import com.pseudonym.audi.exception.CustomException;
import com.pseudonym.audi.exception.ErrorCode;
import com.pseudonym.audi.repository.EmailAuthRepository;
import com.pseudonym.audi.repository.UserRepository;
import com.pseudonym.audi.util.OTP.OTPTokenValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EmailAuthRepository emailAuthRepository;
    private final PasswordEncoder passwordEncoder;

    public User join (UserSingUpDto userSingUpDto) {

        validateDuplicateUser(userSingUpDto.getId());
        User user = toUser(userSingUpDto);
        userRepository.save(user);
        return user;
    }

    private void validateDuplicateUser(String id) {
        userRepository.findById(id).ifPresent(m-> {
            throw new CustomException(ErrorCode.DUPLICATE_USER);
        });
    }

    private String encryptPw(String plainPw) {
        return passwordEncoder.encode(plainPw);
    }

    public boolean checkIdAndEncryptedPw(LoginDto loginDto){

        User user = userRepository.findById(loginDto.getId())
                .orElseThrow(()-> new CustomException(ErrorCode.UNAUTHORIZED_USER));
        if (passwordEncoder.matches(loginDto.getPw(), user.getEncryptedPw())) {
            return true;
        }
        return false;
    }

    private User toUser(UserSingUpDto userSingUpDto){
        User user = new User();
        user.setId(userSingUpDto.getId());
        user.setEncryptedPw(encryptPw(userSingUpDto.getPw()));
        user.setName(userSingUpDto.getName());
        user.setEmail(userSingUpDto.getEmail());
        user.setPhoneNumber(userSingUpDto.getPhoneNumber());

        return user;
    }

    public String getIdByNameAndEmail(UserIdFinderDto userIdFinderDto) {

        User user = userRepository.findByNameAndEmail(userIdFinderDto.getName(), userIdFinderDto.getEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.UNMATCH_ID_AND_EMAIL));
        return user.getId();
    }

    public String initPwByIdAndEmail(UserPwFinderDto userPwFinderDto) {

        String newPlainRandomPw = RandomStringUtils.random(20, 33, 125, true, true);
        User user = userRepository.findByIdAndEmail(userPwFinderDto.getId(), userPwFinderDto.getEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.UNMATCH_ID_AND_EMAIL));
        user.setEncryptedPw(newPlainRandomPw);
        userRepository.save(user);
        return newPlainRandomPw;
    }

    public boolean validOtpCode(OtpCodeDto otpCodeDto) {

        OTPTokenValidator otpTokenValidator = new OTPTokenValidator();
        EmailAuth emailAuth = emailAuthRepository.findById(otpCodeDto.getId())
                        .orElseThrow(()-> new CustomException(ErrorCode.UNMATCH_RESOURCE));
        otpTokenValidator.setSecretKey(emailAuth.getOtpToken());
        return otpTokenValidator.validate(otpCodeDto.getOtpCode());
    }

    public List<User> getUserList() {
        List<User> users = userRepository.findAll();
        return users;
    }

    public boolean checkAuthentication(LoginDto loginDto) {
        EmailAuth emailAuth = emailAuthRepository.findById(loginDto.getId())
                .orElseThrow(()-> new CustomException(ErrorCode.UNAUTHORIZED_EMAIL));
        return emailAuth.getIsAuthorized();
    }
}
