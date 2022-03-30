package com.pseudonym.audi.dto;

import com.pseudonym.audi.domain.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {
    private String id;
    private String pw;
    private String name;
    private String email;
    private String phone;
    private String department;
    private Role role = Role.APPLICANT;
}
