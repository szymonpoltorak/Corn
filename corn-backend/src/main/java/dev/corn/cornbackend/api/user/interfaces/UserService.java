package dev.corn.cornbackend.api.user.interfaces;

import dev.corn.cornbackend.entities.user.data.UserResponse;

public interface UserService {

    UserResponse registerUser(String name, String surname, String username);

}
