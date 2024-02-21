package dev.corn.cornbackend.entities.user.interfaces;

import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.entities.user.data.UserResponse;
import org.mapstruct.Mapper;

/**
 * Mapper for User entity
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    /**
     * Maps User to UserResponse
     * @param user User to map
     * @return UserResponse
     */
    UserResponse toUserResponse(User user);
}
