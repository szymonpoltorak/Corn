package dev.corn.cornbackend.api.backlog.comment.data;

import dev.corn.cornbackend.entities.user.User;
import lombok.Builder;

@Builder
public record BacklogItemCommentResponse(String comment,
                                         User user) {
}
