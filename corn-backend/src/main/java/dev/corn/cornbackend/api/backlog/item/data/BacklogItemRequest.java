package dev.corn.cornbackend.api.backlog.item.data;

import lombok.Builder;

@Builder
public record BacklogItemRequest(String title, String description, long projectMemberId, long sprintId, long projectId) {
}
