package dev.corn.cornbackend.test.backlog.item.data;

import dev.corn.cornbackend.api.backlog.item.data.BacklogItemRequest;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemResponse;
import dev.corn.cornbackend.entities.backlog.item.BacklogItem;
import dev.corn.cornbackend.entities.user.User;

public record AddBacklogItemTestData(
        BacklogItemRequest backlogItemRequest,
        BacklogItemResponse backlogItemResponse,
        BacklogItem backLogItem,
        User user) {
}
