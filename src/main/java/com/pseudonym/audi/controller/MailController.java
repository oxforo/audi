package com.pseudonym.audi.controller;

import com.pseudonym.audi.dto.MailDto;
import com.pseudonym.audi.service.MailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class MailController {
    private final MailService mailService;

    @GetMapping("/mail")
    public String dispMail() {
        return "mail";
    }

    @PostMapping("/mail")
    public void executeMail(MailDto mailDto) {
        mailService.mailSend(mailDto);
    }
}
