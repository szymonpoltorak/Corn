package dev.corn.cornbackend.repositories.project.data;

import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.user.User;
import lombok.Builder;

@Builder
public record ProjectRepositoryTestData(
        Project project1,
        Project project2,
        User project1And2Owner,
        User project1Member,
        User nonProjectMember
) {
}
