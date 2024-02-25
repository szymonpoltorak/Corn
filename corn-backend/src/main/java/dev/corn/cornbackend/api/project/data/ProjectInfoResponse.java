package dev.corn.cornbackend.api.project.data;

import dev.corn.cornbackend.api.project.member.data.ProjectMemberInfoResponse;
import dev.corn.cornbackend.api.sprint.data.SprintResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record ProjectInfoResponse(String name, List<SprintResponse> sprints, long totalNumberOfUsers,
                                  List<ProjectMemberInfoResponse> membersInfo) {
}
