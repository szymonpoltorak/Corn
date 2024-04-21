package dev.corn.cornbackend.api.sprint.data;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record SprintResponse(long sprintId, long projectId, String sprintName, String sprintDescription,
                             LocalDate startDate, LocalDate endDate) {
}
