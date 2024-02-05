package dev.corn.cornbackend.api.user.interfaces;

import dev.corn.cornbackend.entities.user.data.UserResponse;

/**
 * Interface for the user service
 */
@FunctionalInterface
public interface UserService {
    /**
     * Registers a user
     *
     * @param name the name of the user
     * @param surname the surname of the user
     * @param username the username of the user
     * @return the user response
     */
    UserResponse registerUser(String name, String surname, String username);

}
