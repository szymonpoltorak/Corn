package dev.corn.cornbackend.entities.user.data;

import lombok.Builder;

@Builder
public record UserResponse(long userId, String name, String surname, String username) {
}
