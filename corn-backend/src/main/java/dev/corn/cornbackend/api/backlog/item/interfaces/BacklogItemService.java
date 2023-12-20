package dev.corn.cornbackend.api.backlog.item.interfaces;

import dev.corn.cornbackend.api.backlog.item.data.BacklogItemDetails;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemRequest;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemResponse;

import java.util.List;

public interface BacklogItemService {

    BacklogItemResponse getById(long id);

    BacklogItemResponse update(long id, BacklogItemRequest backlogItemRequest);

    BacklogItemResponse deleteById(long id);

    BacklogItemResponse create(BacklogItemRequest backlogItemRequest);

    List<BacklogItemResponse> getBySprintId(long sprintId);

    List<BacklogItemResponse> getByProjectId(long projectId);

    BacklogItemDetails getDetailsById(long id);
}
