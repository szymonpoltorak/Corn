package dev.corn.cornbackend.test.backlog.item.data;

import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.project.member.ProjectMember;
import dev.corn.cornbackend.entities.sprint.Sprint;

public record EntityData(ProjectMember projectmember, Sprint sprint, Project project) {
}
