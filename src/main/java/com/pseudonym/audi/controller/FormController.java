package com.pseudonym.audi.controller;

import com.pseudonym.audi.domain.Request;
import com.pseudonym.audi.dto.RequestFormDto;
import com.pseudonym.audi.service.RequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;


@RestController
@Slf4j
@RequestMapping(value = "/form")
@RequiredArgsConstructor
public class FormController {
    // 가명처리 신청서 생성
    @PostMapping(value = "/request")
    public ResponseEntity createRequestForm(@ModelAttribute @Valid RequestFormDto requestFormDto) {
        Request request = RequestService.submit(requestFormDto);
        URI uri = URI.create("/request"+request.getId());
        log.info("Create Request: "+ request.getId());
        return ResponseEntity.created(uri).build();
    }



    // 가명처리 신청서 목록 확인


    // 가명처리 신청서 수정

    // 가명처리 신청서 삭제
}
