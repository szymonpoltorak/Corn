package dev.corn.cornbackend.api.user;

import dev.corn.cornbackend.api.user.interfaces.UserService;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.entities.user.data.UserResponse;
import dev.corn.cornbackend.entities.user.interfaces.UserMapper;
import dev.corn.cornbackend.entities.user.interfaces.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public final UserResponse registerUser(String name, String surname, String username) {
        log.info("Registering new user with name: {}, surname: {} and username: {}", name, surname, username);

        User user = User.builder()
                .name(name)
                .surname(surname)
                .username(username)
                .build();

        log.info("Instantiated user: {}", user);

        User newUser = userRepository.save(user);

        log.info("Saved user: {}", newUser);

        return userMapper.toUserResponse(user);
    }

}
