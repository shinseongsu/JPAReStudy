DROP TABLE IF EXISTS NOTICE;

create table user
(
    id bigint generated by default as identity,
    email varchar(255),
    password varchar(255),
    phone varchar(255),
    reg_date timestamp,
    user_name varchar(255),
    update_date timestamp,
    primary key (id)
);

create table NOTICE
(
    ID BIGINT auto_increment primary key,
    TITLE     VARCHAR(255),
    CONTENTS  VARCHAR(255),

    HITS            INTEGER,
    LIKES           INTEGER,
    REG_DATE        TIMESTAMP,
    UPDATE_DATE     TIMESTAMP,
    DELETED_DATE    TIMESTAMP,
    DELETED         BOOLEAN,

    USER_ID         BIGINT,
    constraint FK_NOTICE_USER_ID foreign key(USER_ID) references USER(ID)
);