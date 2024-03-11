package dev.corn.cornbackend.api.backlog.item.data;

import lombok.Builder;

import java.util.List;

@Builder
public record BacklogItemResponseList(List<BacklogItemResponse> backlogItemResponseList, long totalNumber) {}
