package ru.S7.social_network.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.S7.social_network.entities.User;
import ru.S7.social_network.exception.AuthException;
import ru.S7.social_network.security.AuthRequest;
import ru.S7.social_network.security.AuthResponse;
import ru.S7.social_network.security.RegistrationRequest;
import ru.S7.social_network.security.jwt.JwtTokenProvider;
import ru.S7.social_network.service.UserService;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegistrationRequest registrationRequest) {
        User user = new User();
        user.setPassword(registrationRequest.getPassword());
        user.setLogin(registrationRequest.getLogin());
        userService.saveUser(user);
        return new ResponseEntity<>("Register is successful!", HttpStatus.OK);
    }

    @PostMapping("/auth")
    public AuthResponse auth(@RequestBody AuthRequest request) throws AuthException {
        User user = userService.findByLoginAndPassword(request.getLogin(),
                                                        request.getPassword());
        String token = jwtTokenProvider.generateToken(user.getLogin());
        return new AuthResponse(token);
    }
}
