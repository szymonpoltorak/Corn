package dev.corn.cornbackend.api.backlog.item.data;

import dev.corn.cornbackend.api.backlog.comment.data.BacklogItemCommentResponse;
import dev.corn.cornbackend.api.project.data.ProjectResponse;
import dev.corn.cornbackend.api.project.member.data.ProjectMemberResponse;
import dev.corn.cornbackend.entities.sprint.data.SprintResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record BacklogItemDetails(List<BacklogItemCommentResponse> comments,
                                 ProjectMemberResponse member,
                                 SprintResponse sprint,
                                 ProjectResponse projectResponse) {
}
