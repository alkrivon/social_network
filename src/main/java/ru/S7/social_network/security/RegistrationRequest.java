package ru.S7.social_network.security;

import lombok.Data;


@Data
public class RegistrationRequest {

    private String login;
    private String username;
    private String password;
}
