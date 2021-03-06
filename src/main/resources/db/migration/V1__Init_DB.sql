
 create table hibernate_sequence (next_val bigint);
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

 create table user_friends (
        user_id bigint not null,
        friend_id bigint not null);

 create table user_invite (
            from_user bigint not null,
            to_user bigint not null);

 alter table user_friends
    add constraint FK11y5boh1e7gh60rdqixyetv3x
    foreign key (friend_id) references users (id);

 alter table user_friends
     add constraint FKk08ugelrh9cea1oew3hgxryw2
     foreign key (user_id) references users (id);

 alter table users
     add constraint FKp56c1712k691lhsyewcssf40f
     foreign key (role_id) references roles (id);

 alter table user_invite
    add constraint FKd95jqup6pwqih3ho39y8m7i6e
    foreign key (to_user) references users (id);

 alter table user_invite
    add constraint FKku0ri6i8hgr1ps8jn5so2u3xb
    foreign key (from_user) references users (id);

