package dev.corn.cornbackend.api.backlog.item.data;

import dev.corn.cornbackend.entities.user.data.UserResponse;
import lombok.Builder;

@Builder
public record BacklogItemResponse(String title, String description, String status, UserResponse assignee,
                                  String itemType) {
}
