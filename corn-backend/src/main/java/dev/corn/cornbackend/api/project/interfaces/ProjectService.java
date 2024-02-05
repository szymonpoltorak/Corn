package dev.corn.cornbackend.api.project.interfaces;

import dev.corn.cornbackend.api.project.data.ProjectResponse;
import dev.corn.cornbackend.entities.user.User;

import java.util.List;

/**
 * Interface for ProjectService
 */
public interface ProjectService {
    /**
     * Adds a new project to the database
     *
     * @param name the name of the project
     * @param user the user who is adding the project
     * @return the response of the project
     */
    ProjectResponse addNewProject(String name, User user);

    /**
     * Gets a list of projects on a page
     *
     * @param page the page number
     * @param user the user who is getting the projects
     * @return the list of project responses
     */
    List<ProjectResponse> getProjectsOnPage(int page, User user);

    /**
     * Updates the name of a project
     *
     * @param name the new name of the project
     * @param projectId the id of the project
     * @param user the user who is updating the project
     * @return the response of the project
     */
    ProjectResponse updateProjectsName(String name, long projectId, User user);

    /**
     * Deletes a project from the database
     *
     * @param projectId the id of the project
     * @param user the user who is deleting the project
     * @return the response of the project
     */
    ProjectResponse deleteProject(long projectId, User user);
}
