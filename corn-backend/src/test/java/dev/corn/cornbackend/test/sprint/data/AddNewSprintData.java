package dev.corn.cornbackend.test.sprint.data;

import dev.corn.cornbackend.api.sprint.data.SprintRequest;
import dev.corn.cornbackend.api.sprint.data.SprintResponse;
import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.sprint.Sprint;

import java.time.LocalDate;

public record AddNewSprintData(Project project, String name, String description, LocalDate startDate, LocalDate endDate) {

    public Sprint asSprint() {
        return new Sprint(0, project, name, description, startDate, endDate);
    }

    public SprintRequest asSprintRequest() {
        return SprintRequest.builder()
                .name(name)
                .projectId(project.getProjectId())
                .description(description)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }

    public SprintResponse asSprintResponse() {
        return SprintResponse.builder()
                .sprintName(name)
                .projectId(project.getProjectId())
                .sprintDescription(description)
                .sprintStartDate(startDate)
                .sprintEndDate(endDate)
                .build();
    }

}
