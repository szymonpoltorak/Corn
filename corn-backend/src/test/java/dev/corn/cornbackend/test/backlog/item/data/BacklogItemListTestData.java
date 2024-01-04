package dev.corn.cornbackend.test.backlog.item.data;

import dev.corn.cornbackend.api.backlog.item.data.BacklogItemResponse;
import dev.corn.cornbackend.entities.backlog.item.BacklogItem;
import lombok.Builder;

import java.util.List;

public record BacklogItemListTestData(
        List<BacklogItem> backlogItems,
        List<BacklogItemResponse> backlogItemResponses
) {
}
