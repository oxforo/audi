package com.pseudonym.audi.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
public class MailDto {

    private String id;
    @Email
    private String email;
}
