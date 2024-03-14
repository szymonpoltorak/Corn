package dev.corn.cornbackend.test.backlog.item.data;

import dev.corn.cornbackend.api.backlog.item.data.BacklogItemDetails;
import dev.corn.cornbackend.entities.backlog.item.BacklogItem;

public record BacklogItemDetailsTestData(
        BacklogItem backlogItem,
        BacklogItemDetails backlogItemDetails
) {
}
