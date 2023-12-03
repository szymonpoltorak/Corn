package dev.corn.cornbackend.entities.project;

import dev.corn.cornbackend.api.project.data.ProjectResponse;
import dev.corn.cornbackend.entities.project.interfaces.ProjectMapper;
import dev.corn.cornbackend.entities.project.interfaces.ProjectMapperImpl;
import dev.corn.cornbackend.entities.sprint.Sprint;
import dev.corn.cornbackend.entities.sprint.data.SprintResponse;
import org.junit.jupiter.api.Test;

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
    final void test_toSprintResponse_shouldReturnNull() {
        // given
        ProjectMapper projectMapper = new ProjectMapperImpl();

        // when
        SprintResponse actual = projectMapper.toSprintResponse(null);

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
}
