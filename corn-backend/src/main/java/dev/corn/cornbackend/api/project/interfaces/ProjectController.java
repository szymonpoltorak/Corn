package dev.corn.cornbackend.api.project.interfaces;

import dev.corn.cornbackend.api.project.data.ProjectResponse;
import dev.corn.cornbackend.entities.user.User;

import java.util.List;

/**
 * Interface for the ProjectController
 */
public interface ProjectController {
    /**
     * Adds a new project to the database
     *
     * @param name the name of the project
     * @param user the user that is adding the project
     * @return the response of the project
     */
    ProjectResponse addNewProject(String name, User user);

    /**
     * Gets a list of projects on a page
     *
     * @param page the page number
     * @param user the user that is getting the projects
     * @return the list of projects
     */
    List<ProjectResponse> getProjectsOnPage(int page, User user);

    /**
     * Updates the name of the project
     *
     * @param name the new name of the project
     * @param projectId the id of the project
     * @param user the user that is updating the project
     * @return the response of the project
     */
    ProjectResponse updateProjectsName(String name, long projectId, User user);

    /**
     * Deletes a project
     *
     * @param projectId the id of the project
     * @param user the user that is deleting the project
     * @return the response of the project
     */
    ProjectResponse deleteProject(long projectId, User user);
}
