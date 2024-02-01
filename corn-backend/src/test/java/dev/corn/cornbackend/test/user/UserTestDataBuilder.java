package dev.corn.cornbackend.test.user;

import dev.corn.cornbackend.entities.user.User;

import java.util.Collections;

public final class UserTestDataBuilder {

    public static User createSampleUser() {
        return User.builder()
                .name("Name")
                .surname("Surname")
                .userId(1L)
                .username("Username")
                .authorities(Collections.emptyList())
                .build();
    }
}
