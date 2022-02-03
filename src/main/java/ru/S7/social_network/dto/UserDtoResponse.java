package ru.S7.social_network.dto;

import lombok.Data;

@Data
public class UserDtoResponse {

    private Long id;
    private String username;
    private String login;
    private Long role;

}
