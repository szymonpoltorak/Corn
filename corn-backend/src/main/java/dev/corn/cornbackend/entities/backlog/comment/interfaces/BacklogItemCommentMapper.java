package dev.corn.cornbackend.entities.backlog.comment.interfaces;

import dev.corn.cornbackend.api.backlog.comment.data.BacklogItemCommentResponse;
import dev.corn.cornbackend.entities.backlog.comment.BacklogItemComment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BacklogItemCommentMapper {

    BacklogItemCommentResponse toBacklogItemCommentResponse(BacklogItemComment backlogItemComment);
}
