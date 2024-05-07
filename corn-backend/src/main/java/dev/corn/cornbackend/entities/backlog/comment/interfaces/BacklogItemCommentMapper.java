package dev.corn.cornbackend.entities.backlog.comment.interfaces;

import dev.corn.cornbackend.api.backlog.comment.data.BacklogItemCommentResponse;
import dev.corn.cornbackend.entities.backlog.comment.BacklogItemComment;
import dev.corn.cornbackend.entities.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for converting BacklogItemComment to BacklogItemCommentResponse
 */
@Mapper(componentModel = "spring")
public interface BacklogItemCommentMapper {
    /**
     * Converts a BacklogItemComment to a BacklogItemCommentResponse
     *
     * @param backlogItemComment The BacklogItemComment to convert
     * @return The converted BacklogItemCommentResponse
     */
    @Mapping(source = "backlogItemComment.commentDate", target = "commentTime")
    @Mapping(target = "canEdit", expression = "java(user.equals(backlogItemComment.getUser()) || user.equals(backlogItemComment.getBacklogItem().getProject().getOwner()))")
    @Named("toBacklogItemCommentResponse")
    BacklogItemCommentResponse toBacklogItemCommentResponse(BacklogItemComment backlogItemComment, User user);
}
