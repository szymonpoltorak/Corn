package dev.corn.cornbackend.test.backlog.item.comment.data;

import dev.corn.cornbackend.api.backlog.comment.data.BacklogItemCommentRequest;
import dev.corn.cornbackend.api.backlog.comment.data.BacklogItemCommentResponse;
import dev.corn.cornbackend.entities.backlog.comment.BacklogItemComment;
import dev.corn.cornbackend.entities.user.User;

public record BacklogItemCommentTestData(
        BacklogItemCommentRequest backlogItemCommentRequest,
        BacklogItemComment backlogItemComment,
        BacklogItemCommentResponse backlogItemCommentResponse,
        BacklogItemComment backlogItemCommentToSave,
        User user
) {
}
