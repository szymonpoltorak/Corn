package dev.corn.cornbackend.test.project;

import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.test.project.data.AddNewProjectData;

import java.util.Collections;

public final class ProjectTestDataBuilder {
    public static AddNewProjectData addNewProjectData() {
        String name = "projectName";
        User user = User
                .builder()
                .username("example@gmail.com")
                .name("example")
                .build();
        Project project = Project
                .builder()
                .name(name)
                .owner(user)
                .sprints(Collections.emptyList())
                .build();
        return new AddNewProjectData(user, project);
    }

    private ProjectTestDataBuilder() {
    }
}
