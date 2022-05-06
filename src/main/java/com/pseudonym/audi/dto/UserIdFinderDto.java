package com.pseudonym.audi.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserIdFinderDto {

    @NotBlank
    String name;

    @NotBlank
    @Email
    String email;
}
