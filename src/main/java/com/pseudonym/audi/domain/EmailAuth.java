package com.pseudonym.audi.domain;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@DynamicUpdate
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "email_auth")
public class EmailAuth {

    private static final Long MAX_EXPIRE_TIME = 5L;

    @Id
    private String id;

    @Email
    private String email;
    private String authToken;
    private Boolean isAuthorized;
    private LocalDateTime expireDate;

    @Nullable
    private String otpToken;

    public EmailAuth(String id, String email, String authToken) {
        this.id = id;
        this.email = email;
        this.authToken = authToken;
        this.isAuthorized = false;
        this.expireDate = LocalDateTime.now().withNano(0).plusMinutes(MAX_EXPIRE_TIME);
        this.otpToken = null;
    }

    public void useAuthToken() {
        this.isAuthorized = true;
    }

    public void initOtpToken(String secretKey) {
        this.otpToken = secretKey;
    }
}
