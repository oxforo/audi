package com.pseudonym.audi.controller;

import com.pseudonym.audi.dto.MailAuthDto;
import com.pseudonym.audi.dto.MailDto;
import com.pseudonym.audi.service.MailService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/mail")
public class MailController {
    private MailService mailService;

    @PostMapping("/auth")
    public ResponseEntity getMailAuth(@Valid  @RequestBody MailDto mailDto) {
        boolean isSend = mailService.sendMailAuth(mailDto);
        return ResponseEntity.ok(isSend);
    }

    @PutMapping("/auth")
    public ResponseEntity activeMailAuth(@Valid @ModelAttribute MailAuthDto mailAuthDto) {
        boolean isValidMailAuth = mailService.validMailAuth(mailAuthDto);
        return ResponseEntity.ok(isValidMailAuth);
    }

    @PostMapping("/otp")
    public ResponseEntity getMailOTP(@Valid @RequestBody MailDto mailDto) {
        boolean isSend = mailService.sendMailOTP(mailDto);
        return ResponseEntity.ok(isSend);
    }
}
