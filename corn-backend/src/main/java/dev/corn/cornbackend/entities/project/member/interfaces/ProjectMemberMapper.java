package dev.corn.cornbackend.entities.project.member.interfaces;

import dev.corn.cornbackend.api.project.member.data.ProjectMemberResponse;
import dev.corn.cornbackend.entities.project.member.ProjectMember;

@FunctionalInterface
public interface ProjectMemberMapper {
    ProjectMemberResponse toProjectMemberResponse(ProjectMember projectMember);
}
