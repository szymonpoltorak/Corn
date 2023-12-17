package dev.corn.cornbackend.entities.user;

import dev.corn.cornbackend.entities.user.data.UserResponse;
import dev.corn.cornbackend.entities.user.interfaces.UserMapper;
import dev.corn.cornbackend.entities.user.interfaces.UserMapperImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserMapperTest {
    @Test
    final void test_toUserResponse_shouldReturnUserResponseObject() {
        // given
        UserMapper userMapper = new UserMapperImpl();
        User user = new User();

        user.setName("Name");
        user.setSurname("Surname");
        user.setUsername("Username");
        user.setUserId(123L);

        // when
        UserResponse userResponse = userMapper.toUserResponse(user);

        // then
        assertEquals("Name", userResponse.name(), "Name should be equal");
        assertEquals("Surname", userResponse.surname(), "Surname should be equal");
        assertEquals("Username", userResponse.username(), "Username should be equal");
        assertEquals(123L, userResponse.userId(), "UserId should be equal");
    }

    @Test
    final void test_toUserResponse_shouldReturnNull() {
        // given
        UserMapper userMapper = new UserMapperImpl();
        User user = null;

        // when
        UserResponse userResponse = userMapper.toUserResponse(user);

        // then
        assertNull(userResponse, "UserResponse should be null");
    }
}
