package com.pseudonym.audi.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class LoginDto {

    @NotBlank
    private String id;

    @NotBlank
    private String pw;
}
