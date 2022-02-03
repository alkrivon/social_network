package ru.S7.social_network.dto;

import lombok.Data;

@Data
public class UserDtoRequest {

    private String username;
    private String login;
    private String password;
    private Long role;
}
