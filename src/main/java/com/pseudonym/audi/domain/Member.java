package com.pseudonym.audi.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Member {

    @Id
    private String id;
    private String pw;
    private String name;
    private String email;
    private String phone;
    private String department;
    private Role role;
}


