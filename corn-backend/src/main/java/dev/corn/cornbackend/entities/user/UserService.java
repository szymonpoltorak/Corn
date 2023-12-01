package dev.corn.cornbackend.entities.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User registerUser(String name, String surname, String username) {
        User user = new User(0, name, surname, username);
        return userRepository.save(user);
    }

}
