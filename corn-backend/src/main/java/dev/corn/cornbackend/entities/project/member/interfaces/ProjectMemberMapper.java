package dev.corn.cornbackend.entities.project.member.interfaces;

import dev.corn.cornbackend.api.project.member.data.ProjectMemberResponse;
import dev.corn.cornbackend.entities.project.member.ProjectMember;

/**
 * Mapper for ProjectMember
 */
@FunctionalInterface
public interface ProjectMemberMapper {
    /**
     * Maps a ProjectMember to a ProjectMemberResponse
     *
     * @param projectMember the ProjectMember to map
     * @return the ProjectMemberResponse
     */
    ProjectMemberResponse toProjectMemberResponse(ProjectMember projectMember);
}
