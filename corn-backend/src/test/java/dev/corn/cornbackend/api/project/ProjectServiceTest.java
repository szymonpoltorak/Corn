package dev.corn.cornbackend.api.project;

import dev.corn.cornbackend.api.project.data.ProjectResponse;
import dev.corn.cornbackend.entities.project.interfaces.ProjectMapper;
import dev.corn.cornbackend.entities.project.interfaces.ProjectRepository;
import dev.corn.cornbackend.test.project.ProjectTestDataBuilder;
import dev.corn.cornbackend.test.project.data.AddNewProjectData;
import dev.corn.cornbackend.utils.exceptions.project.ProjectDoesNotExistException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {
    static final String SHOULD_THROW_PROJECT_DOES_NOT_EXIST_EXCEPTION = "Should throw ProjectDoesNotExistException";
    static final String PROJECT_RESPONSE_SHOULD_BE_EQUAL_TO_EXPECTED = "ProjectResponse should be equal to expected";
    @InjectMocks
    private ProjectServiceImpl projectService;

    @Mock
    private ProjectRepository projectRepository;

    private static final AddNewProjectData ADD_PROJECT_DATA = ProjectTestDataBuilder.addNewProjectData();

    private final ProjectMapper MAPPER = Mappers.getMapper(ProjectMapper.class);

    @Mock
    private ProjectMapper projectMapper;

    @Test
    final void test_addNewProject_shouldAddNewProject() {
        // given
        ProjectResponse expected = MAPPER.toProjectResponse(ADD_PROJECT_DATA.project());

        // when
        when(projectRepository.save(ADD_PROJECT_DATA.project()))
                .thenReturn(ADD_PROJECT_DATA.project());
        when(projectMapper.toProjectResponse(ADD_PROJECT_DATA.project()))
                .thenReturn(expected);

        ProjectResponse actual = projectService
                .addNewProject(ADD_PROJECT_DATA.project().getName(), ADD_PROJECT_DATA.owner());

        // then
        assertEquals(expected, actual, PROJECT_RESPONSE_SHOULD_BE_EQUAL_TO_EXPECTED);
        verify(projectRepository).save(ADD_PROJECT_DATA.project());
    }

    @Test
    final void test_getProjectsOnPage_shouldGetOneElementListOfProjects() {
        // given
        final int page = 0;
        Pageable pageable = PageRequest.of(page, 20);
        ProjectResponse response = MAPPER.toProjectResponse(ADD_PROJECT_DATA.project());
        List<ProjectResponse> expected = List.of(response);

        // when
        when(projectRepository.findAllByOwnerOrderByName(ADD_PROJECT_DATA.owner(), pageable))
                .thenReturn(new PageImpl<>(List.of(ADD_PROJECT_DATA.project())));
        when(projectMapper.toProjectResponse(ADD_PROJECT_DATA.project()))
                .thenReturn(response);

        List<ProjectResponse> actual = projectService.getProjectsOnPage(page, ADD_PROJECT_DATA.owner());

        // then
        assertEquals(expected, actual, "List of ProjectResponse should be empty");
    }

    @Test
    final void test_updateProjectName_shouldThrowProjectDoesNotExistException() {
        // given
        final String newName = "newName";
        final long projectId = 1L;

        // when
        when(projectRepository.findById(projectId))
                .thenReturn(Optional.empty());

        // then
        assertThrows(ProjectDoesNotExistException.class,
                () -> projectService.updateProjectsName(newName, projectId),
                SHOULD_THROW_PROJECT_DOES_NOT_EXIST_EXCEPTION);
    }

    @Test
    final void test_updateProjectName_shouldUpdateProjectsName() {
        // given
        final String newName = "newName";
        final long projectId = 1L;
        ProjectResponse expected = MAPPER.toProjectResponse(ADD_PROJECT_DATA.project());

        // when
        when(projectRepository.findById(projectId))
                .thenReturn(Optional.of(ADD_PROJECT_DATA.project()));
        when(projectRepository.save(ADD_PROJECT_DATA.project()))
                .thenReturn(ADD_PROJECT_DATA.project());
        when(projectMapper.toProjectResponse(ADD_PROJECT_DATA.project()))
                .thenReturn(expected);

        ProjectResponse actual = projectService.updateProjectsName(newName, projectId);

        // then
        assertEquals(expected, actual, PROJECT_RESPONSE_SHOULD_BE_EQUAL_TO_EXPECTED);
    }

    @Test
    final void test_deleteProject_shouldThrowProjectDoesNotExistException() {
        // given
        final long projectId = 1L;

        // when
        when(projectRepository.findById(projectId))
                .thenReturn(Optional.empty());

        // then
        assertThrows(ProjectDoesNotExistException.class,
                () -> projectService.deleteProject(projectId),
                SHOULD_THROW_PROJECT_DOES_NOT_EXIST_EXCEPTION);
    }

    @Test
    final void test_deleteProject_shouldDeleteProject() {
        // given
        final long projectId = 1L;
        ProjectResponse expected = MAPPER.toProjectResponse(ADD_PROJECT_DATA.project());

        // when
        when(projectRepository.findById(projectId))
                .thenReturn(Optional.of(ADD_PROJECT_DATA.project()));
        when(projectMapper.toProjectResponse(ADD_PROJECT_DATA.project()))
                .thenReturn(expected);

        ProjectResponse actual = projectService.deleteProject(projectId);

        // then
        assertEquals(expected, actual, PROJECT_RESPONSE_SHOULD_BE_EQUAL_TO_EXPECTED);
    }
}
