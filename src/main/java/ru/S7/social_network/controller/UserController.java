package ru.S7.social_network.controller;


import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.S7.social_network.dto.UserDtoResponse;
import ru.S7.social_network.exception.NotFoundException;
import ru.S7.social_network.security.CustomUserDetails;
import ru.S7.social_network.service.UserService;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Api( tags = "Social Network")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Найти пользователя по id")
    @GetMapping("/get/{userId}")
    public UserDtoResponse getUser(@PathVariable Long userId) throws NotFoundException {
        return userService.getUserById(userId);
    }

    @Operation(summary = "Найти пользователя по имени")
    @GetMapping("/get")
    public UserDtoResponse getUserByName(@RequestParam String username) throws NotFoundException {
        return userService.getUserByUsername(username);
    }

    @Operation(summary = "Получить список друзей")
    @GetMapping("/getFriends")
    public List<UserDtoResponse> getFriends(@ApiIgnore @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return userService.getFriends(customUserDetails.getId());
    }

    @Operation(summary = "Получить список заявок в друзья")
    @GetMapping("/getInvitations")
    public List<UserDtoResponse> getInvitations(@ApiIgnore @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return userService.getInvitations(customUserDetails.getId());
    }

    @Operation(summary = "Отправить заявку в друзья")
    @PostMapping("/addInvite/{toUserId}")
    public ResponseEntity<String> createInvite(@ApiIgnore @AuthenticationPrincipal CustomUserDetails customUserDetails,
                                               @PathVariable Long toUserId) throws NotFoundException {
        userService.createInvite(customUserDetails.getId(), toUserId);
        return new ResponseEntity<>("Invite has been sent!", HttpStatus.OK);
    }

    @Operation(summary = "Добавить в друзья")
    @PostMapping("/addToFriends/{candidateId}")
    public ResponseEntity<String> addToFriend(@ApiIgnore @AuthenticationPrincipal CustomUserDetails customUserDetails,
                                              @PathVariable Long candidateId) throws NotFoundException {
        userService.addToFriends(customUserDetails.getId(), candidateId);
        return new ResponseEntity<>("Friend added!", HttpStatus.OK);
    }

    @Operation(summary = "Удалить из друзей")
    @DeleteMapping("/deleteFriend/{friendId}")
    public ResponseEntity<String> deleteFriend(@ApiIgnore @AuthenticationPrincipal CustomUserDetails customUserDetails,
                                               @PathVariable Long friendId) throws NotFoundException {
        userService.deleteFriend(customUserDetails.getId(), friendId);
        return new ResponseEntity<>("Friend deleted!", HttpStatus.OK);
    }
}
