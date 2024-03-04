package dev.corn.cornbackend.entities.project.member.interfaces;

import dev.corn.cornbackend.entities.project.member.ProjectMember;
import dev.corn.cornbackend.entities.user.data.UserResponse;

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
    UserResponse mapProjectMememberToUserResponse(ProjectMember projectMember);
}
