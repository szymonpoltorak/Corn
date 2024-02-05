package dev.corn.cornbackend.api.backlog.comment.interfaces;

import dev.corn.cornbackend.api.backlog.comment.data.BacklogItemCommentRequest;
import dev.corn.cornbackend.api.backlog.comment.data.BacklogItemCommentResponse;
import dev.corn.cornbackend.entities.user.User;

/**
 * Interface for the BacklogItemCommentController
 */
public interface BacklogItemCommentController {
    /**
     * Adds a new comment to a backlog item
     *
     * @param request The request containing the comment and the backlog item id
     * @param user The user that is adding the comment
     * @return The response containing the comment
     */
    BacklogItemCommentResponse addNewComment(BacklogItemCommentRequest request, User user);

    /**
     * Updates a comment on a backlog item
     *
     * @param commentId The id of the comment to update
     * @param comment The new comment
     * @param user The user that is updating the comment
     * @return The response containing the updated comment
     */
    BacklogItemCommentResponse updateComment(long commentId, String comment, User user);

    /**
     * Deletes a comment on a backlog item
     *
     * @param commentId The id of the comment to delete
     * @param user The user that is deleting the comment
     * @return The response containing the deleted comment
     */
    BacklogItemCommentResponse deleteComment(long commentId, User user);

    /**
     * Gets a comment on a backlog item
     *
     * @param commentId The id of the comment to get
     * @param user The user that is getting the comment
     * @return The response containing the comment
     */
    BacklogItemCommentResponse getComment(long commentId, User user);
}
