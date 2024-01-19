package dev.corn.cornbackend.api.sprint.data;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record SprintRequest(long projectId, String name, LocalDate startDate, LocalDate endDate, String description) {
}
