package dev.corn.cornbackend.api.backlog.comment.interfaces;

import dev.corn.cornbackend.api.backlog.comment.data.BacklogItemCommentRequest;
import dev.corn.cornbackend.api.backlog.comment.data.BacklogItemCommentResponse;
import dev.corn.cornbackend.entities.user.User;

public interface BacklogItemCommentController {

    BacklogItemCommentResponse addNewComment(BacklogItemCommentRequest request, User user);

    BacklogItemCommentResponse updateComment(long commentId, String comment);

    BacklogItemCommentResponse deleteComment(long commentId);

    BacklogItemCommentResponse getComment(long commentId);
}
