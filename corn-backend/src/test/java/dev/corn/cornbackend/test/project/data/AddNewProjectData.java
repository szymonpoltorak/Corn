package dev.corn.cornbackend.test.project.data;

import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.user.User;

public record AddNewProjectData(User owner, Project project) {
}
