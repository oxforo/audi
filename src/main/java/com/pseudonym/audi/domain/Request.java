package com.pseudonym.audi.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table
public class Request {

    @Id
    private long id;

    @Column
    private String title;

    @Column
    private String writer;

    @Column
    private String purpose;

    @Column
    private String kind;

    @Column
    private String createDate;

    @Column
    private String usingEnvironment;

    @Column
    private String securityLevel;
}
