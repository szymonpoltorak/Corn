package dev.corn.cornbackend.entities.backlog.comment.interfaces;

import dev.corn.cornbackend.api.backlog.comment.data.BacklogItemCommentResponse;
import dev.corn.cornbackend.entities.backlog.comment.BacklogItemComment;
import org.mapstruct.Mapper;

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
    BacklogItemCommentResponse toBacklogItemCommentResponse(BacklogItemComment backlogItemComment);
}
