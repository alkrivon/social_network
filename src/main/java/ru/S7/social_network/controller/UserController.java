package ru.S7.social_network.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.S7.social_network.dto.UserDtoResponse;
import ru.S7.social_network.exception.NotFoundException;
import ru.S7.social_network.security.CustomUserDetails;
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

    @GetMapping("/getFriends")
    public List<UserDtoResponse> getFriends(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return userService.getFriends(customUserDetails.getId());
    }

    @GetMapping("/getInvitations")
    public List<UserDtoResponse> getInvitations(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return userService.getInvitations(customUserDetails.getId());
    }

    @PostMapping("/addInvite/{toUserId}")
    public ResponseEntity<String> createInvite(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                               @PathVariable Long toUserId) throws NotFoundException {
        userService.createInvite(customUserDetails.getId(), toUserId);
        return new ResponseEntity<>("The application has been sent!", HttpStatus.OK);
    }

    @PostMapping("/addToFriends/{candidateId}")
    public ResponseEntity<String> addToFriend(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                              @PathVariable Long candidateId) throws NotFoundException {
        userService.addToFriends(customUserDetails.getId(), candidateId);
        return new ResponseEntity<>("Friend added!", HttpStatus.OK);
    }

    @DeleteMapping("/deleteFriend/{friendId}")
    public ResponseEntity<String> deleteFriend(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                               @PathVariable Long friendId) {
        userService.deleteFriend(customUserDetails.getId(), friendId);
        return new ResponseEntity<>("Friend deleted!", HttpStatus.OK);
    }
}
