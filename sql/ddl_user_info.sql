DROP TABLE IF EXISTS user_info;


CREATE TABLE user_info(
    id varchar(20),
    encrypt_pw varchar(255),
    name varchar(20),
    email varchar(50),
    phone_number varchar(20),
    role varchar(20),
    PRIMARY KEY (id)
);


