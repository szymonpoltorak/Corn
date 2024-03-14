package dev.corn.cornbackend.test.backlog.item.data;

import dev.corn.cornbackend.api.backlog.item.data.BacklogItemRequest;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemResponse;
import dev.corn.cornbackend.entities.backlog.item.BacklogItem;

public record UpdateBacklogItemTestData(
        BacklogItemRequest backlogItemRequest,
        BacklogItemResponse backlogItemResponse,
        BacklogItem backLogItem,
        BacklogItem updatedBacklogItem
) {
}
