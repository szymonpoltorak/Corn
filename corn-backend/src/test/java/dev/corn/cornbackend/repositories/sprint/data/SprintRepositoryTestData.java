package dev.corn.cornbackend.repositories.sprint.data;

import dev.corn.cornbackend.entities.sprint.Sprint;
import dev.corn.cornbackend.entities.user.User;
import lombok.Builder;

@Builder
public record SprintRepositoryTestData(
        Sprint sprint,
        long projectId,
        User projectOwner,
        User projectMember,
        User nonProjectMember
) {
}
