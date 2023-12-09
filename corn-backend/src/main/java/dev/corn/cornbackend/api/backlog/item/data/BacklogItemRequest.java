package dev.corn.cornbackend.api.backlog.item.data;

public record BacklogItemRequest(String title, String description, long projectMemberId, long sprintId, long projectId) {
}
