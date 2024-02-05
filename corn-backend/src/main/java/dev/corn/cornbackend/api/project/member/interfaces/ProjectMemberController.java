package dev.corn.cornbackend.api.project.member.interfaces;

import dev.corn.cornbackend.api.project.member.data.ProjectMemberResponse;
import dev.corn.cornbackend.entities.user.User;

import java.util.List;

/**
 * Interface for ProjectMemberController
 */
public interface ProjectMemberController {
    /**
     * Adds a member to a project
     *
     * @param username the username of the member to add
     * @param projectId the id of the project to add the member to
     * @param user the user making the request
     * @return a ProjectMemberResponse object
     */
    ProjectMemberResponse addMemberToProject(String username, long projectId, User user);

    /**
     * Gets a list of project members
     *
     * @param projectId the id of the project to get the members of
     * @param page the page number to get the members from
     * @param user the user making the request
     * @return a list of ProjectMemberResponse objects
     */
    List<ProjectMemberResponse> getProjectMembers(long projectId, int page, User user);

    /**
     * Removes a member from a project
     *
     * @param username the username of the member to remove
     * @param projectId the id of the project to remove the member from
     * @param user the user making the request
     * @return a ProjectMemberResponse object
     */
    ProjectMemberResponse removeMemberFromProject(String username, long projectId, User user);
}
