package ru.S7.social_network.security;

import lombok.Data;

@Data
public class AuthRequest {

    private String login;
    private String password;
}
