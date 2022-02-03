package ru.S7.social_network.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.S7.social_network.dto.UserDtoResponse;
import ru.S7.social_network.entities.User;
import ru.S7.social_network.exception.NotFoundException;
import ru.S7.social_network.mappers.UserMapper;
import ru.S7.social_network.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserDtoResponse getUserById(Long id) throws NotFoundException {
        Optional<User> user = userRepository.findUserById(id);
        if (user.isPresent()) {
            return UserMapper.INSTANCE.userToUserDto(user.get());
        }
        throw new NotFoundException("There is no user with id = " + id);
    }

    @Transactional(readOnly = true)
    public UserDtoResponse getUserByUsername(String username) throws NotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return UserMapper.INSTANCE.userToUserDto(user.get());
        }
        throw new NotFoundException("There is no user with name = " + username);
    }

    @Transactional(readOnly = true)
    public List<UserDtoResponse> getFriends(Long id) {
        return userRepository.findUserById(id).get().getFriends()
                .stream()
                .map(UserMapper.INSTANCE::userToUserDto)
                .collect(Collectors.toList());
    }
}
