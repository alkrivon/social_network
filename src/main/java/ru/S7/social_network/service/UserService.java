package ru.S7.social_network.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.S7.social_network.dto.UserDtoResponse;
import ru.S7.social_network.entities.Role;
import ru.S7.social_network.entities.User;
import ru.S7.social_network.exception.AuthException;
import ru.S7.social_network.exception.NotFoundException;
import ru.S7.social_network.mappers.UserMapper;
import ru.S7.social_network.repository.RoleRepository;
import ru.S7.social_network.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

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

    @Transactional
    public void createInvite(Long fromUserId, Long toUserId) throws NotFoundException {
        Optional<User> fromUser = userRepository.findUserById(fromUserId);
        Optional<User> toUser = userRepository.findUserById(toUserId);
        if (toUser.isPresent() &&
            !toUser.get().getInvitations().contains(fromUser.get()) &&
            toUserId != fromUserId &&
            !toUser.get().getFriends().contains(fromUser.get())) {

            toUser.get().getInvitations().add(fromUser.get());

        } else if (toUser.get().getInvitations().contains(fromUser.get())) {
            throw new NotFoundException("You have already applied for friends!");
        } else if (toUser.get().getFriends().contains(fromUser.get())) {
            throw new NotFoundException("The user has already been added to friends!");
        }
        else throw new NotFoundException("There is no user with id = " + toUserId);
    }

    @Transactional(readOnly = true)
    public List<UserDtoResponse> getInvitations(Long userId) {
        return userRepository.findUserById(userId).get().getInvitations()
                .stream()
                .map(UserMapper.INSTANCE::userToUserDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void addToFriends(Long userId, Long candidateId) throws NotFoundException {
        Optional<User> user = userRepository.findUserById(userId);
        Optional<User> candidate = userRepository.findUserById(candidateId);
        if (user.isPresent() && candidate.isPresent()) {

            List<User> userFriends = user.get().getFriends();
            List<User> candidateFriends = candidate.get().getFriends();

            if (user.get().getInvitations().contains(candidate.get())) {

                userFriends.add(candidate.get());
                candidateFriends.add(user.get());
                user.get().getInvitations().remove(candidate.get());
            }
            else if (!user.get().getInvitations().contains(candidate.get())) {
                throw new NotFoundException("There is no invite!");
            }
        }
        else throw new NotFoundException("There is no user with id = " + candidateId);
    }

    @Transactional
    public void deleteFriend(Long userId, Long friendId) throws NotFoundException {
        Optional<User> user = userRepository.findUserById(userId);
        Optional<User> friend = userRepository.findUserById(friendId);
        if (user.isPresent() && friend.isPresent() &&
            user.get().getFriends().contains(friend.get())) {

            user.get().getFriends().remove(friend.get());
            friend.get().getFriends().remove(user.get());
        } else throw new NotFoundException("There is no friend with id = " + friendId);
    }

    public User saveUser(User user) {
        Role role = roleRepository.findByName("ROLE_USER");
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login).get();
    }

    public User findByLoginAndPassword(String login, String password) throws AuthException {
        User user = findByLogin(login);
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        throw new AuthException("Invalid username or password!");
    }
}
