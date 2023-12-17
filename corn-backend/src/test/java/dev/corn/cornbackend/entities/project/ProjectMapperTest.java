package dev.corn.cornbackend.entities.project;

import dev.corn.cornbackend.api.project.data.ProjectResponse;
import dev.corn.cornbackend.entities.project.interfaces.ProjectMapper;
import dev.corn.cornbackend.entities.project.interfaces.ProjectMapperImpl;
import dev.corn.cornbackend.entities.sprint.Sprint;
import dev.corn.cornbackend.api.sprint.data.SprintResponse;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ProjectMapperTest {
    @Test
    final void test_toProjectResponse_shouldReturnNull() {
        // given
        ProjectMapper projectMapper = new ProjectMapperImpl();

        // when
        ProjectResponse actual = projectMapper.toProjectResponse(null);

        // then
        assertNull(actual, "Should return null");
    }

    @Test
    final void test_sprintListToSprintResponseList_shouldReturnListOfSprints() {
        // given
        ProjectMapper projectMapper = new ProjectMapperImpl();
        Project project = Project
                .builder()
                .sprints(List.of(new Sprint()))
                .build();

        // when
        ProjectResponse actual = projectMapper.toProjectResponse(project);

        // then
        assertNotNull(actual.sprints(), "Should return null");
    }

    @Test
    final void test_sprintListToSprintResponseList_shouldReturnNull() {
        // given
        ProjectMapper projectMapper = new ProjectMapperImpl();
        Project project = Project
                .builder()
                .sprints(null)
                .build();

        // when
        ProjectResponse actual = projectMapper.toProjectResponse(project);

        // then
        assertNull(actual.sprints(), "Should return null");
    }

    @Test
    final void test_sprintToSprintResponse_shouldReturnNull() {
        // given
        ProjectMapper projectMapper = new ProjectMapperImpl();
        List<Sprint> list = new ArrayList<>();
        list.add(null);
        Project project = Project
                .builder()
                .sprints(list)
                .build();

        // when
        ProjectResponse actual = projectMapper.toProjectResponse(project);

        // then
        assertNull(actual.sprints().get(0), "Should return null");
    }
}
