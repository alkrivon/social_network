package ru.S7.social_network.controller;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.S7.social_network.entities.User;
import ru.S7.social_network.exception.AuthException;
import ru.S7.social_network.repository.UserRepository;
import ru.S7.social_network.security.AuthRequest;
import ru.S7.social_network.security.AuthResponse;
import ru.S7.social_network.security.RegistrationRequest;
import ru.S7.social_network.security.jwt.JwtTokenProvider;
import ru.S7.social_network.service.UserService;

@RequiredArgsConstructor
@RestController
@Api( tags = "Authorization and registration")
public class AuthController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Operation(summary = "Регистрация")
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegistrationRequest registrationRequest) {

        if (userRepository.findByLogin(registrationRequest.getLogin()).isPresent()) {
            return new ResponseEntity<>("A user with this login already exists!", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setUsername(registrationRequest.getUsername());
        user.setLogin(registrationRequest.getLogin());
        user.setPassword(registrationRequest.getPassword());
        userService.saveUser(user);
        return new ResponseEntity<>("Register is successful!", HttpStatus.OK);
    }

    @Operation(summary = "Авторизация")
    @PostMapping("/auth")
    public AuthResponse auth(@RequestBody AuthRequest request) throws AuthException {
        User user = userService.findByLoginAndPassword(request.getLogin(),
                                                        request.getPassword());
        String token = jwtTokenProvider.generateToken(user.getLogin());
        return new AuthResponse("Bearer " + token);
    }
}
