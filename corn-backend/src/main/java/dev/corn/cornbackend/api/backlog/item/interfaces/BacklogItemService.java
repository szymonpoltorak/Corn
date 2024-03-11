package dev.corn.cornbackend.api.backlog.item.interfaces;

import dev.corn.cornbackend.api.backlog.item.data.BacklogItemDetails;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemResponseList;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemRequest;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemResponse;
import dev.corn.cornbackend.entities.user.User;

import java.util.List;

/**
 * Service for backlog items
 */
public interface BacklogItemService {
    /**
     * Get backlog item by id
     *
     * @param id   id of the backlog item
     * @param user user that is requesting the item
     * @return response with the backlog item
     */
    BacklogItemResponse getById(long id, User user);

    /**
     * Update backlog item
     *
     * @param id                 id of the backlog item
     * @param backlogItemRequest request with the new data
     * @param user               user that is updating the item
     * @return response with the updated backlog item
     */
    BacklogItemResponse update(long id, BacklogItemRequest backlogItemRequest, User user);

    /**
     * Delete backlog item by id
     *
     * @param id   id of the backlog item
     * @param user user that is deleting the item
     * @return response with the deleted backlog item
     */
    BacklogItemResponse deleteById(long id, User user);

    /**
     * Create backlog item
     *
     * @param backlogItemRequest request with the new data
     * @param user               user that is creating the item
     * @return response with the created backlog item
     */
    BacklogItemResponse create(BacklogItemRequest backlogItemRequest, User user);

    /**
     * Get all backlog items by sprint id
     *
     * @param user user that is requesting the items
     * @param sprintId id of the sprint
     * @return response with the list of backlog items
     */
    List<BacklogItemResponse> getBySprintId(long sprintId, User user);

    /**
     * Get all backlog items by project id
     *
     * @param user user that is requesting the items
     * @param projectId id of the project
     * @return response with the list of backlog items
     */
    BacklogItemResponseList getByProjectId(long projectId, int pageNumber, String sortBy, String order, User user);

    /**
     * Get backlog item details by id
     *
     * @param id   id of the backlog item
     * @param user user that is requesting the item
     * @return response with the backlog item details
     */
    BacklogItemDetails getDetailsById(long id, User user);
}
