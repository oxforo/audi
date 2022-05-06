package com.pseudonym.audi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_APPLIER("ROLE_PROPOSER"),
    ROLE_EVALUATOR("ROLE_EVALUATOR"),
    ROLE_MANAGER("ROLE_MANAGER");

    private String role;
}
