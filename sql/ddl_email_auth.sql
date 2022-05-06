DROP TABLE IF EXISTS email_auth;

CREATE TABLE email_auth(
                          id varchar(20),
                          email varchar(50),
                          auth_token varchar(6),
                          is_authorized boolean,
                          expire_date datetime,
                          otp_token varchar(32),
                          PRIMARY KEY (id)
);