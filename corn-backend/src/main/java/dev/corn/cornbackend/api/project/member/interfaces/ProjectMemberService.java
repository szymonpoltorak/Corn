package dev.corn.cornbackend.api.project.member.interfaces;

import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.entities.user.data.UserResponse;

import java.util.List;

/**
 * Service to manage the project members
 */
public interface ProjectMemberService {
    /**
     * Add a assignee to a project
     *
     * @param username the username of the assignee to add
     * @param projectId the id of the project to add the assignee to
     * @param user the user making the request
     * @return project assignee response data
     */
    UserResponse addMemberToProject(String username, long projectId, User user);

    /**
     * Get the members of a project
     *
     * @param projectId the id of the project to get the members of
     * @param page the page of the members to get
     * @param user the user making the request
     * @return list of project assignee response data
     */
    List<UserResponse> getProjectMembers(long projectId, int page, User user);

    /**
     * Remove a assignee from a project
     *
     * @param username the username of the assignee to remove
     * @param projectId the id of the project to remove the assignee from
     * @param user the user making the request
     * @return project assignee response data
     */
    UserResponse removeMemberFromProject(String username, long projectId, User user);

    /**
     * Get the info of the members of a project
     *
     * @param projectId the id of the project to get the members info of
     * @return list of project assignee info response data
     */
    List<UserResponse> getProjectMembersInfo(long projectId);

    /**
     * Get the total number of members of a project
     *
     * @param projectId the id of the project to get the total number of members of
     * @return the total number of members of the project
     */
    long getTotalNumberOfMembers(long projectId);
}
