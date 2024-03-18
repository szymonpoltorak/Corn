package dev.corn.cornbackend.repositories.sprint.data;

import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.sprint.Sprint;
import dev.corn.cornbackend.entities.user.User;
import lombok.Builder;

@Builder
public record SprintRepositoryTestData(
        Sprint currentSprint,
        long projectId,
        User projectOwner,
        User projectMember,
        User nonProjectMember,
        Sprint futureSprint,
        Sprint finishedSprint,
        Project project
) {
}
