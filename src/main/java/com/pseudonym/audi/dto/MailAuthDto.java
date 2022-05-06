package com.pseudonym.audi.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class MailAuthDto {

    @NotBlank
    private String id;

    @NotBlank
    private String AuthKey;
}
