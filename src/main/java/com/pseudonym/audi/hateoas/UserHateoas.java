package com.pseudonym.audi.hateoas;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pseudonym.audi.domain.User;
import org.springframework.hateoas.RepresentationModel;

public class UserHateoas extends RepresentationModel<UserHateoas> {
    private final User user;

    @JsonCreator
    public UserHateoas(@JsonProperty("user") User user){
        this.user = user;
    }
}
