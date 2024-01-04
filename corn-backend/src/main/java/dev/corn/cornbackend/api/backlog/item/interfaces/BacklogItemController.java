package dev.corn.cornbackend.api.backlog.item.interfaces;

import dev.corn.cornbackend.api.backlog.item.data.BacklogItemDetails;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemRequest;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemResponse;
import dev.corn.cornbackend.entities.user.User;

import java.util.List;

public interface BacklogItemController {

    BacklogItemResponse getById(long id, User user);

    BacklogItemResponse update(long id, BacklogItemRequest backlogItemRequest, User user);

    BacklogItemResponse deleteById(long id, User user);

    BacklogItemResponse create(BacklogItemRequest backlogItemRequest, User user);

    List<BacklogItemResponse> getBySprintId(long sprintId, User user);

    List<BacklogItemResponse> getByProjectId(long projectId, User user);

    BacklogItemDetails getDetailsById(long id, User user);
}
