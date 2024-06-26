package dev.corn.cornbackend.api.backlog.item.data;

import dev.corn.cornbackend.api.backlog.comment.data.BacklogItemCommentResponse;
import dev.corn.cornbackend.api.project.data.ProjectResponse;
import dev.corn.cornbackend.api.sprint.data.SprintResponse;
import dev.corn.cornbackend.entities.user.data.UserResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record BacklogItemDetails(List<BacklogItemCommentResponse> comments,
                                 UserResponse assignee,
                                 SprintResponse sprint,
                                 ProjectResponse project) {
}
