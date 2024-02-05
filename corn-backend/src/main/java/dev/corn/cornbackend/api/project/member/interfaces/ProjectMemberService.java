package dev.corn.cornbackend.api.project.member.interfaces;

import dev.corn.cornbackend.api.project.member.data.ProjectMemberResponse;
import dev.corn.cornbackend.entities.user.User;

import java.util.List;

/**
 * Service to manage the project members
 */
public interface ProjectMemberService {
    /**
     * Add a member to a project
     *
     * @param username the username of the member to add
     * @param projectId the id of the project to add the member to
     * @param user the user making the request
     * @return project member response data
     */
    ProjectMemberResponse addMemberToProject(String username, long projectId, User user);

    /**
     * Get the members of a project
     *
     * @param projectId the id of the project to get the members of
     * @param page the page of the members to get
     * @param user the user making the request
     * @return list of project member response data
     */
    List<ProjectMemberResponse> getProjectMembers(long projectId, int page, User user);

    /**
     * Remove a member from a project
     *
     * @param username the username of the member to remove
     * @param projectId the id of the project to remove the member from
     * @param user the user making the request
     * @return project member response data
     */
    ProjectMemberResponse removeMemberFromProject(String username, long projectId, User user);
}
