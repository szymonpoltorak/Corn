package dev.corn.cornbackend.api.project;

import dev.corn.cornbackend.api.project.data.ProjectResponse;
import dev.corn.cornbackend.entities.project.interfaces.ProjectMapper;
import dev.corn.cornbackend.entities.project.interfaces.ProjectRepository;
import dev.corn.cornbackend.test.project.ProjectTestDataBuilder;
import dev.corn.cornbackend.test.project.data.AddNewProjectData;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ProjectRepository.class)
class ProjectServiceTest {
    @InjectMocks
    private ProjectServiceImpl projectService;

    @Mock
    private ProjectRepository projectRepository;

    private final AddNewProjectData ADD_PROJECT_DATA = ProjectTestDataBuilder.addNewProjectData();

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
        assertEquals(expected, actual, "ProjectResponse should be equal to expected");
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
}
