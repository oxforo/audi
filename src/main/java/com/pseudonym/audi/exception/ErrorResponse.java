package com.pseudonym.audi.exception;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class ErrorResponse {
    private String message;
    private int status;
    private List<String> errors;

    private ErrorResponse(final ErrorCode code, final List<String> errors) {
        this.message = code.getMessage();
        this.status = code.getHttpStatus().value();
        this.errors = errors;
    }

    private ErrorResponse(final ErrorCode code, final String error) {
        this.message = code.getMessage();
        this.status = code.getHttpStatus().value();
        this.errors = Arrays.asList(error);

    }

    public static ErrorResponse of(final ErrorCode code, final String error) {
        return new ErrorResponse(code, error);
    }

    public static ErrorResponse of(final ErrorCode code, final List<String> errors) {
        return new ErrorResponse(code, errors);
    }
}
