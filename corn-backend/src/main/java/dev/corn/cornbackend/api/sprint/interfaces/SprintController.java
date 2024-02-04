package dev.corn.cornbackend.api.sprint.interfaces;

import dev.corn.cornbackend.api.sprint.data.SprintRequest;
import dev.corn.cornbackend.api.sprint.data.SprintResponse;
import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.user.User;

import java.time.LocalDate;
import java.util.List;

/**
 * The SprintController interface defines the contract for the Sprint Controller.
 * It provides methods for managing sprints in a project management application.
 */
public interface SprintController {

    /**
     * Adds a new sprint to the system.
     *
     * @param sprintRequest The details of the sprint to be added.
     * @param user The user who is adding the sprint.
     * @return The details of the added sprint.
     */
    SprintResponse addNewSprint(SprintRequest sprintRequest, User user);

    /**
     * Retrieves the details of a specific sprint by its ID.
     *
     * @param sprintId The ID of the sprint to retrieve.
     * @param user The user who is requesting the sprint details.
     * @return The details of the requested sprint.
     */
    SprintResponse getSprintById(long sprintId, User user);

    /**
     * Retrieves a list of sprints on a specific page.
     *
     * @param page The page number to retrieve sprints from.
     * @param projectId The ID of the project to retrieve sprints from.
     * @param user The user who is requesting the sprints.
     * @return A list of sprints on the requested page.
     */
    List<SprintResponse> getSprintsOnPage(int page, long projectId, User user);

    /**
     * Updates the name of a specific sprint.
     *
     * @param name The new name for the sprint.
     * @param sprintId The ID of the sprint to update.
     * @param user The user who is updating the sprint.
     * @return The details of the updated sprint.
     */
    SprintResponse updateSprintsName(String name, long sprintId, User user);

    /**
     * Updates the description of a specific sprint.
     *
     * @param description The new description for the sprint.
     * @param sprintId The ID of the sprint to update.
     * @param user The user who is updating the sprint.
     * @return The details of the updated sprint.
     */
    SprintResponse updateSprintsDescription(String description, long sprintId, User user);

    /**
     * Updates the start date of a specific sprint.
     *
     * @param startDate The new start date for the sprint.
     * @param sprintId The ID of the sprint to update.
     * @param user The user who is updating the sprint.
     * @return The details of the updated sprint.
     */
    SprintResponse updateSprintsStartDate(LocalDate startDate, long sprintId, User user);

    /**
     * Updates the end date of a specific sprint.
     *
     * @param endDate The new end date for the sprint.
     * @param sprintId The ID of the sprint to update.
     * @param user The user who is updating the sprint.
     * @return The details of the updated sprint.
     */
    SprintResponse updateSprintsEndDate(LocalDate endDate, long sprintId, User user);

    /**
     * Deletes a specific sprint from the system.
     *
     * @param sprintId The ID of the sprint to delete.
     * @param user The user who is deleting the sprint.
     * @return The details of the deleted sprint.
     */
    SprintResponse deleteSprint(long sprintId, User user);
}
