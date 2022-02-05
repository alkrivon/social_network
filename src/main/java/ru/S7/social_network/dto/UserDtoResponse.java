package ru.S7.social_network.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDtoResponse {

    private Long id;
    private String username;
    private String login;
    private Long role;

}
