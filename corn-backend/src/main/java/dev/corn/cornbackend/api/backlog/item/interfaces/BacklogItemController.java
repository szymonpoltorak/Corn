package dev.corn.cornbackend.api.backlog.item.interfaces;

import dev.corn.cornbackend.api.backlog.item.data.BacklogItemRequest;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemResponse;

import java.util.List;

public interface BacklogItemController {

    public BacklogItemResponse getById(long id);

    public BacklogItemResponse update(long id, BacklogItemRequest backlogItemRequest);

    public BacklogItemResponse deleteById(long id);

    public BacklogItemResponse create(BacklogItemRequest backlogItemRequest);

    public List<BacklogItemResponse> getBySprintId(long sprintId);

    public List<BacklogItemResponse> getByProjectId(long projectId);
}
