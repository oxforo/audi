package com.pseudonym.audi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestFormDto {

    private long id;
    private String title;
    private String writer;
    private String purpose;
    private String kind;
    private String createDate;
    private String usingEnvironment;
    private String securityLevel;
}
