package dev.corn.cornbackend.api.sprint.data;

import java.time.LocalDate;

public record SprintResponse(long sprintId, long projectId, String sprintName, String sprintDescription,
                             LocalDate sprintStartDate, LocalDate sprintEndDate) {
}
