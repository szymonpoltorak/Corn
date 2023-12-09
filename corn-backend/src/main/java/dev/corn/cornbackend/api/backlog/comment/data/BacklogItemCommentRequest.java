package dev.corn.cornbackend.api.backlog.comment.data;

import lombok.Builder;

@Builder
public record BacklogItemCommentRequest(String comment, long backlogItemId) {
}
