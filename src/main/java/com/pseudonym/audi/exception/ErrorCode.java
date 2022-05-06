package com.pseudonym.audi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 400 BAD_REQUEST : 필수 요청 변수가 없거나 잘못된 경우
    INVALID_INPUT_VALUE(NOT_FOUND, "유효하지 않은 값입니다."),
    INVALID_ENCODING_OTP_URL(NOT_FOUND, "OTP URL 생성시 인코딩에러가 발생했습니다."),
    // 401 UNAUTHORIZED : 인증되지 않은 사용자
    UNAUTHORIZED_EMAIL(UNAUTHORIZED, "email 인증이 되지 않았습니다."),
    UNAUTHORIZED_JWT(UNAUTHORIZED, "JWT 인증이 되지 않았습니다."),
    UNAUTHORIZED_USER(UNAUTHORIZED, "아이디 혹은 패스워드를 잘못입력했습니다."),
    UNAUTHORIZED_AUKEY(UNAUTHORIZED, "아이디 혹은 이메일 인증 키가 일치하지 않습니다."),

    // 404 NOT_FOUND : RESOURCE를 찾을 수 없음
    UNMATCH_ID_AND_EMAIL(NOT_FOUND, "입력하신 이름과 이메일에 일치하는 계정이 존재하지 않습니다."),
    UNMATCH_RESOURCE(NOT_FOUND, "입력하신 정보를 찾을 수 없습니다."),

    // 409 CONFLICT : 현재 서버의 상태와 충돌
    DUPLICATE_USER(CONFLICT, "이미 존재하는 회원입니다."),

    // 500 Internal Server Error : 서버 내부 문제 발생
    MISS_EMAIL(INTERNAL_SERVER_ERROR, "이메일 전송 시 에러가 발생했습니다."),
    MISPRINT_QR_CODE_IMAGE(INTERNAL_SERVER_ERROR, "QR 코드 이미지를 생성할 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
