package dev.corn.cornbackend.api.project;

import dev.corn.cornbackend.api.project.data.ProjectInfoResponse;
import dev.corn.cornbackend.api.project.data.ProjectResponse;
import dev.corn.cornbackend.api.project.interfaces.ProjectService;
import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.project.interfaces.ProjectMapper;
import dev.corn.cornbackend.entities.user.data.UserResponse;
import dev.corn.cornbackend.test.project.ProjectTestDataBuilder;
import dev.corn.cornbackend.test.project.data.AddNewProjectData;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static dev.corn.cornbackend.api.project.ProjectServiceTest.PROJECT_RESPONSE_SHOULD_BE_EQUAL_TO_EXPECTED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ProjectService.class)
class ProjectControllerTest {
    @InjectMocks
    private ProjectControllerImpl projectController;

    private static final AddNewProjectData ADD_PROJECT_DATA = ProjectTestDataBuilder.addNewProjectData();

    private final ProjectMapper MAPPER = Mappers.getMapper(ProjectMapper.class);

    @Mock
    private ProjectService projectService;

    @Test
    final void test_addNewProject_shouldAddNewProject() {
        // given
        Project project = ADD_PROJECT_DATA.project();
        ProjectInfoResponse expected = MAPPER.toProjectInfoResponse(project, Collections.emptyList(), 0L,
                ADD_PROJECT_DATA.owner());

        // when
        when(projectService.addNewProject(ADD_PROJECT_DATA.project().getName(), ADD_PROJECT_DATA.owner()))
                .thenReturn(expected);

        ProjectInfoResponse actual = projectController
                .addNewProject(ADD_PROJECT_DATA.project().getName(), ADD_PROJECT_DATA.owner());

        // then
        assertEquals(expected, actual, PROJECT_RESPONSE_SHOULD_BE_EQUAL_TO_EXPECTED);
    }

    @Test
    final void test_getProjectsOnPage_shouldGetProjects() {
        // given
        Project project = ADD_PROJECT_DATA.project();
        List<UserResponse> members = List.of(new UserResponse(1, "full", "name", "username"));
        List<ProjectInfoResponse> expected = List.of(MAPPER.toProjectInfoResponse(project, members, 1L,
                ADD_PROJECT_DATA.owner()));
        int page = 0;

        // when
        when(projectService.getProjectsOnPage(page, ADD_PROJECT_DATA.owner()))
                .thenReturn(expected);

        List<ProjectInfoResponse> actual = projectController
                .getProjectsOnPage(page, ADD_PROJECT_DATA.owner());

        // then
        assertEquals(expected, actual, PROJECT_RESPONSE_SHOULD_BE_EQUAL_TO_EXPECTED);
    }

    @Test
    final void test_updateProjectsName_shouldUpdateName() {
        // given
        Project project = ADD_PROJECT_DATA.project();
        ProjectResponse expected = MAPPER.toProjectResponse(project, ADD_PROJECT_DATA.owner());

        // when
        when(projectService.updateProjectsName(ADD_PROJECT_DATA.project().getName(), 0L, ADD_PROJECT_DATA.owner()))
                .thenReturn(expected);

        ProjectResponse actual = projectController
                .updateProjectsName(ADD_PROJECT_DATA.project().getName(), 0L, ADD_PROJECT_DATA.owner());

        // then
        assertEquals(expected, actual, PROJECT_RESPONSE_SHOULD_BE_EQUAL_TO_EXPECTED);
    }

    @Test
    final void test_deleteProject_shouldDeleteProject() {
        // given
        long projectId = 0L;
        Project project = ADD_PROJECT_DATA.project();
        ProjectResponse expected = MAPPER.toProjectResponse(project, ADD_PROJECT_DATA.owner());

        // when
        when(projectService.deleteProject(projectId, ADD_PROJECT_DATA.owner()))
                .thenReturn(expected);

        ProjectResponse actual = projectController.deleteProject(projectId, ADD_PROJECT_DATA.owner());

        // then
        assertEquals(expected, actual, PROJECT_RESPONSE_SHOULD_BE_EQUAL_TO_EXPECTED);
    }
}
