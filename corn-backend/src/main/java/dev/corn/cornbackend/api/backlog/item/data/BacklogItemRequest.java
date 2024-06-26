package dev.corn.cornbackend.api.backlog.item.data;

import dev.corn.cornbackend.entities.backlog.item.enums.ItemStatus;
import dev.corn.cornbackend.entities.backlog.item.enums.ItemType;
import lombok.Builder;

@Builder
public record BacklogItemRequest(String title, String description, Long projectMemberId, Long sprintId,
                                 Long projectId, ItemType itemType, ItemStatus itemStatus) {
}
