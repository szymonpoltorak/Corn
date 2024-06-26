package dev.corn.cornbackend.api.project.data;

import dev.corn.cornbackend.api.sprint.data.SprintResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record ProjectResponse(String name, List<SprintResponse> sprints, boolean isOwner) {
}
