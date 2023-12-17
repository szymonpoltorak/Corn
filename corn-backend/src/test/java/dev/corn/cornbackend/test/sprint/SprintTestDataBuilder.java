package dev.corn.cornbackend.test.sprint;

import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.test.sprint.data.AddNewSprintData;

import java.time.LocalDate;
import java.util.Collections;

public final class SprintTestDataBuilder {
    public static AddNewSprintData addNewProjectData() {
        User user = User
                .builder()
                .username("example@gmail.com")
                .name("example")
                .build();
        Project project = Project
                .builder()
                .name("projectName")
                .owner(user)
                .sprints(Collections.emptyList())
                .build();
        return new AddNewSprintData(project, "nazwa sprintu", "opis sprintu", LocalDate.now().plusDays(7), LocalDate.now().plusDays(14));
    }

    private SprintTestDataBuilder() {
    }
}
