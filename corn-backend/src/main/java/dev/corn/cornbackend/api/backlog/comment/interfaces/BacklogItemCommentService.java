package dev.corn.cornbackend.api.backlog.comment.interfaces;

import dev.corn.cornbackend.api.backlog.comment.data.BacklogItemCommentRequest;
import dev.corn.cornbackend.api.backlog.comment.data.BacklogItemCommentResponse;
import dev.corn.cornbackend.entities.user.User;

public interface BacklogItemCommentService {

    BacklogItemCommentResponse addNewComment(BacklogItemCommentRequest request, User user);

    BacklogItemCommentResponse updateComment(long commentId, String comment, User user);

    BacklogItemCommentResponse deleteComment(long commentId, User user);

    BacklogItemCommentResponse getComment(long commentId, User user);
}
