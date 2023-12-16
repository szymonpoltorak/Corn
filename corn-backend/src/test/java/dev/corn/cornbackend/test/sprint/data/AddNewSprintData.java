package dev.corn.cornbackend.test.sprint.data;

import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.sprint.Sprint;

import java.time.LocalDate;

public record AddNewSprintData(Project project, String name, String description, LocalDate startDate, LocalDate endDate) {

    public Sprint asSprint() {
        return new Sprint(0, project, name, description, startDate, endDate);
    }

}
