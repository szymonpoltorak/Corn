package dev.corn.cornbackend.api.project.data;

import dev.corn.cornbackend.api.sprint.data.SprintResponse;
import dev.corn.cornbackend.entities.user.data.UserResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record ProjectInfoResponse(long projectId, String name, List<SprintResponse> sprints, long totalNumberOfUsers,
                                  List<UserResponse> membersInfo, boolean isOwner) {
}
