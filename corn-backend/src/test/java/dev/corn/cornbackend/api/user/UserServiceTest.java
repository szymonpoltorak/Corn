package dev.corn.cornbackend.api.user;

import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.entities.user.data.UserResponse;
import dev.corn.cornbackend.entities.user.interfaces.UserMapper;
import dev.corn.cornbackend.entities.user.interfaces.UserMapperImpl;
import dev.corn.cornbackend.entities.user.interfaces.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {UserRepository.class, UserMapper.class})
class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    private static final UserMapper MAPPER = new UserMapperImpl();

    @Test
    final void test_registerUser_shouldRegisterUser() {
        // given
        String name = "name";
        String surname = "surname";
        String username = "username";
        User user = User.builder()
                .name(name)
                .surname(surname)
                .username(username)
                .build();
        UserResponse expected = MAPPER.toUserResponse(user);

        // when
        when(userRepository.save(user))
                .thenReturn(user);
        when(userMapper.toUserResponse(user))
                .thenReturn(expected);

        UserResponse actual = userService.registerUser(name, surname, username);

        // then
        assertEquals(expected, actual, "UserResponse should be equal to expected");
        verify(userRepository).save(user);
    }
}
