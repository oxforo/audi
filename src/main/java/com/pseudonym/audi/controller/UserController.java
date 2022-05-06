package com.pseudonym.audi.controller;

import com.pseudonym.audi.auth.JwtTokenProvider;
import com.pseudonym.audi.auth.UserAuthentication;
import com.pseudonym.audi.domain.User;
import com.pseudonym.audi.dto.*;
import com.pseudonym.audi.exception.CustomException;
import com.pseudonym.audi.exception.ErrorCode;
import com.pseudonym.audi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping(value = "/sign-up")
    public ResponseEntity createUser(@ModelAttribute @Valid UserSingUpDto userSingUpDto) {
        User newUser = userService.join(userSingUpDto);
        URI uri = URI.create("/users/"+newUser.getId());
        log.info("Create User:"+ newUser.getId());
        return ResponseEntity.created(uri).build();
    }

    // 사용자 조회
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/user")
    public ResponseEntity getUsers() {
        List<User> users = userService.getUserList();
        return ResponseEntity.ok(users);
    }

    // 로그인 - ID/PW 검증
    @PostMapping(value = "/sign-in")
    public ResponseEntity login(@ModelAttribute @Valid LoginDto loginDto) {

        boolean isLogin = userService.checkIdAndEncryptedPw(loginDto);
        boolean isAuth = userService.checkAuthentication(loginDto);
        if(!(isLogin && isAuth)) {
            log.error("UNAUTHORIZED_USER");
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }
        log.info("Login:"+loginDto.getId());
        return ResponseEntity.ok(isLogin);
    }

    // 아이디 찾기
    @GetMapping(value = "/id")
    public ResponseEntity findId(@Valid @ModelAttribute UserIdFinderDto userIdFinderDto){

        String returnId = userService.getIdByNameAndEmail(userIdFinderDto);
        log.info("Found ID:"+returnId);
        return ResponseEntity.ok(returnId);
    }

    // 비밀번호 초기화
    @PutMapping(value = "/pw")
    public ResponseEntity initializePw(@Valid @ModelAttribute UserPwFinderDto userPwFinderDto){

        String initPw = userService.initPwByIdAndEmail(userPwFinderDto);
        return ResponseEntity.ok(initPw);
    }

    // 로그인 - OTP 인증
    @PostMapping(value = "/otp")
    public ResponseEntity checkOtpCode(@Valid @ModelAttribute OtpCodeDto otpCodeDto) {
        boolean isValidOtpCode = userService.validOtpCode(otpCodeDto);
        if(!isValidOtpCode){
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }
        Authentication authentication = new UserAuthentication(otpCodeDto.getId(), null, null);
        String token = JwtTokenProvider.generateToken(authentication);
        Token response = Token.builder().token(token).build();

        return ResponseEntity.ok(response);
    }
}
