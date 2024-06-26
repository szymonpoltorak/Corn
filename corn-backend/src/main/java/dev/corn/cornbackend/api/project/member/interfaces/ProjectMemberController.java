package dev.corn.cornbackend.api.project.member.interfaces;

import dev.corn.cornbackend.api.project.member.data.ProjectMemberInfoExtendedResponse;
import dev.corn.cornbackend.api.project.member.data.ProjectMemberList;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.entities.user.data.UserResponse;

import java.util.List;

/**
 * Interface for ProjectMemberController
 */
public interface ProjectMemberController {
    /**
     * Adds a assignee to a project
     *
     * @param username the username of the assignee to add
     * @param projectId the id of the project to add the assignee to
     * @param user the user making the request
     * @return a UserResponse object
     */
    UserResponse addMemberToProject(String username, long projectId, User user);

    /**
     * Gets a list of project members
     *
     * @param projectId the id of the project to get the members of
     * @param page the page number to get the members from
     * @param user the user making the request
     * @return a list of UserResponse objects and total number of project members
     */
    ProjectMemberList getProjectMembers(long projectId, int page, User user);

    /**
     * Removes a assignee from a project
     *
     * @param username the username of the assignee to remove
     * @param projectId the id of the project to remove the assignee from
     * @param user the user making the request
     * @return a UserResponse object
     */
    UserResponse removeMemberFromProject(String username, long projectId, User user);

    /**
     * Returns the project member id of the caller for a given project
     *
     * @param projectId the id of the project
     * @param user the user making the request
     * @return a ProjectMemberIdResponse object
     */
    ProjectMemberInfoExtendedResponse getProjectMemberId(long projectId, User user);

    /**
     * Gets a list of all project members
     *
     * @param projectId the id of the project to get the members of
     * @param user the user making the request
     * @return a list of UserResponse objects
     */
    List<ProjectMemberInfoExtendedResponse> getAllProjectMembers(long projectId, User user);
}
