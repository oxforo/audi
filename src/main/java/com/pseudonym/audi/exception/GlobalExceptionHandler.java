package com.pseudonym.audi.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleValidRequestObjectExceptionHandler(MethodArgumentNotValidException e){
       List<String> validationList = e.getBindingResult()
               .getFieldErrors()
               .stream()
               .map(fieldError -> fieldError.getDefaultMessage())
               .collect(Collectors.toList());

       final ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, validationList);
        return new ResponseEntity<>(errorResponse,HttpStatus.valueOf(errorResponse.getStatus()));
    }

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> handleValidModelObjectExceptionHandler(BindException e){
        List<String> validationList = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.toList());
        final ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, validationList);
        return new ResponseEntity<>(errorResponse,HttpStatus.valueOf(errorResponse.getStatus()));
    }

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        final ErrorResponse errorResponse = ErrorResponse.of(e.getErrorCode(), e.getMessage());
        log.error(e.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.valueOf(errorResponse.getStatus()));
    }
}
