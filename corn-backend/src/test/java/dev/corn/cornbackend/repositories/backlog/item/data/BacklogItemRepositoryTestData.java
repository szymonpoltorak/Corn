package dev.corn.cornbackend.repositories.backlog.item.data;

import dev.corn.cornbackend.entities.backlog.item.BacklogItem;
import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.sprint.Sprint;
import dev.corn.cornbackend.entities.user.User;
import lombok.Builder;

@Builder
public record BacklogItemRepositoryTestData(
        BacklogItem backlogItem,
        BacklogItem backlogItemWithoutSprint,
        User owner,
        User projectMember,
        User nonProjectMember,
        Project project,
        Sprint sprint
) {
}
