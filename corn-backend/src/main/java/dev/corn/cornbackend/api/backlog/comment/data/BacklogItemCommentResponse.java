package dev.corn.cornbackend.api.backlog.comment.data;

import dev.corn.cornbackend.entities.user.data.UserResponse;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record BacklogItemCommentResponse(String comment,
                                         UserResponse user,
                                         LocalDateTime commentTime,
                                         LocalDateTime lastEditTime,
                                         long backlogItemCommentId,
                                         boolean canEdit) {
}
