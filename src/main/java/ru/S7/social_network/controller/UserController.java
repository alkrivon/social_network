package ru.S7.social_network.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.S7.social_network.dto.UserDtoResponse;
import ru.S7.social_network.exception.NotFoundException;
import ru.S7.social_network.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/get/{userId}")
    public UserDtoResponse getUser(@PathVariable Long userId) throws NotFoundException {
        return userService.getUserById(userId);
    }

    @GetMapping("/get")
    public UserDtoResponse getUserByName(@RequestParam String username) throws NotFoundException {
        return userService.getUserByUsername(username);
    }

    @GetMapping("/getFriends/{userId}")
    public List<UserDtoResponse> getFriends(@PathVariable Long userId) {
        return userService.getFriends(userId);
    }
}
