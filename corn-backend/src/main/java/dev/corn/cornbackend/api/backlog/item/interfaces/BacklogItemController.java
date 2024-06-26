package dev.corn.cornbackend.api.backlog.item.interfaces;

import dev.corn.cornbackend.api.backlog.item.data.BacklogItemDetails;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemRequest;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemResponse;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemResponseList;
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
     * Get backlog items by sprint id on given page and sort by given field in given order
     *
     * @param sprintId id of the sprint
     * @param pageNumber number of page
     * @param sortBy name of field to sort backlog items by
     * @param order order of sort ("ASC" or "DESC")
     * @param user     user to get the backlog items for
     * @return the backlog items
     */
    BacklogItemResponseList getBySprintId(long sprintId, int pageNumber, String sortBy, String order, User user);

    /**
     * Get backlog items by project id on given page and sort by given field in given order
     *
     * @param projectId id of the project
     * @param pageNumber number of page
     * @param sortBy name of field to sort backlog items by
     * @param order order of sort ("ASC" or "DESC")
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

    /**
     * Get all backlog items that aren't assigned to any sprint
     * @param projectId id of the project
     * @param pageNumber number of page
     * @param sortBy name of field to sort backlog items by
     * @param order order of sort ("ASC" or "DESC")
     * @param user      user to get the backlog items for
     * @return the backlog items
     */

    BacklogItemResponseList getAllWithoutSprint(long projectId, int pageNumber, String sortBy, String order, User user);

    /**
     * Get all backlog items associated with a given sprint
     *
     * @param sprintId sprint id
     * @param user caller instance
     * @return the backlog items
     */
    List<BacklogItemResponse> getAllBySprintId(long sprintId, User user);

    /**
     * Update specific fields of a backlog item
     *
     * @param id                 id of the backlog item
     * @param backlogItemRequest request containing changed fields (null fields will remain unaffected)
     * @param user               caller instance
     * @return the updated backlog item
     */
    BacklogItemResponse partialUpdate(long id, BacklogItemRequest backlogItemRequest, User user);
}
