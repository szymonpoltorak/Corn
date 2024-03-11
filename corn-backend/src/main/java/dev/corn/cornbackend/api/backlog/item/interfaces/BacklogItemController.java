package dev.corn.cornbackend.api.backlog.item.interfaces;

import dev.corn.cornbackend.api.backlog.item.data.BacklogItemDetails;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemResponseList;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemRequest;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemResponse;
import dev.corn.cornbackend.entities.user.User;

import java.util.List;

/**
 * Interface for the BacklogItemController
 */
public interface BacklogItemController {
    /**
     * Get backlog item by id
     *
     * @param id   id of the backlog item
     * @param user user to get the backlog item for
     * @return the backlog item
     */
    BacklogItemResponse getById(long id, User user);

    /**
     * Update backlog item by id
     *
     * @param id                 id of the backlog item
     * @param backlogItemRequest new data for the backlog item
     * @param user               backlog item owner
     * @return the updated backlog item
     */
    BacklogItemResponse update(long id, BacklogItemRequest backlogItemRequest, User user);

    /**
     * Delete backlog item by id
     *
     * @param id   id of the backlog item
     * @param user user to delete the backlog item for
     * @return the deleted backlog item
     */
    BacklogItemResponse deleteById(long id, User user);

    /**
     * Create backlog item
     *
     * @param backlogItemRequest new data for the backlog item
     * @param user               user to create the backlog item for
     * @return the created backlog item
     */
    BacklogItemResponse create(BacklogItemRequest backlogItemRequest, User user);

    /**
     * Get backlog items by sprint id on given page
     *
     * @param sprintId id of the sprint
     * @param user     user to get the backlog items for
     * @return the backlog items
     */
    List<BacklogItemResponse> getBySprintId(long sprintId, User user);

    /**
     * Get backlog items by project id
     *
     * @param projectId id of the project
     * @param pageNumber number of page
     * @param user      user to get the backlog items for
     * @return the backlog items
     */
    BacklogItemResponseList getByProjectId(long projectId, int pageNumber, String sortBy, String order, User user);

    /**
     * Get backlog item details by id
     *
     * @param id   id of the backlog item
     * @param user user to get the backlog item details for
     * @return the backlog item details
     */
    BacklogItemDetails getDetailsById(long id, User user);
}
