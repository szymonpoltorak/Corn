package dev.corn.cornbackend.api.backlog.item.data;

import lombok.Builder;

@Builder
public record BacklogItemResponse(String title, String description, String status) {
}
