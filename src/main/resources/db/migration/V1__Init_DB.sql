 create table hibernate_sequence (next_val bigint);
 insert into hibernate_sequence values ( 1 );
 insert into hibernate_sequence values ( 1 );

 create table roles (
        id bigint not null,
        name varchar(255),
        primary key (id));

 create table users (
        id bigint not null,
        login varchar(255),
        password varchar(255),
        username varchar(255),
        role_id bigint,
        primary key (id));