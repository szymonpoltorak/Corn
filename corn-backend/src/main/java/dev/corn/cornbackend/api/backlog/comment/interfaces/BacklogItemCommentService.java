package dev.corn.cornbackend.api.backlog.comment.interfaces;

import dev.corn.cornbackend.api.backlog.comment.data.BacklogItemCommentRequest;
import dev.corn.cornbackend.api.backlog.comment.data.BacklogItemCommentResponse;
import dev.corn.cornbackend.api.backlog.comment.data.BacklogItemCommentResponseList;
import dev.corn.cornbackend.entities.user.User;

/**
 * Service for handling backlog item comments
 */
public interface BacklogItemCommentService {
    /**
     * Adds a new comment to a backlog item
     *
     * @param request the request containing the comment and the backlog item id
     * @param user the user adding the comment
     * @return the response containing the new comment
     */
    BacklogItemCommentResponse addNewComment(BacklogItemCommentRequest request, User user);

    /**
     * Updates a comment on a backlog item
     *
     * @param commentId the id of the comment to update
     * @param comment the new comment
     * @param user the user updating the comment
     * @return the response containing the updated comment
     */
    BacklogItemCommentResponse updateComment(long commentId, String comment, User user);

    /**
     * Deletes a comment on a backlog item
     *
     * @param commentId the id of the comment to delete
     * @param user the user deleting the comment
     * @return the response containing the deleted comment
     */
    BacklogItemCommentResponse deleteComment(long commentId, User user);

    /**
     * Gets a comment on a backlog item
     *
     * @param commentId the id of the comment to get
     * @param user the user getting the comment
     * @return the response containing the comment
     */
    BacklogItemCommentResponse getComment(long commentId, User user);

    BacklogItemCommentResponseList getCommentsForBacklogItem(long backlogItemId, int pageNumber, User user);
}
