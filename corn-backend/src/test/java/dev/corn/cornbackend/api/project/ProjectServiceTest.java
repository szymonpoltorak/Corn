package dev.corn.cornbackend.api.project;

import dev.corn.cornbackend.api.project.data.ProjectInfoResponse;
import dev.corn.cornbackend.api.project.data.ProjectResponse;
import dev.corn.cornbackend.api.project.member.data.ProjectMemberInfoResponse;
import dev.corn.cornbackend.api.project.member.interfaces.ProjectMemberService;
import dev.corn.cornbackend.entities.project.interfaces.ProjectMapper;
import dev.corn.cornbackend.entities.project.interfaces.ProjectRepository;
import dev.corn.cornbackend.entities.project.member.ProjectMember;
import dev.corn.cornbackend.entities.project.member.interfaces.ProjectMemberRepository;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {
    static final String SHOULD_THROW_PROJECT_DOES_NOT_EXIST_EXCEPTION = "Should throw ProjectDoesNotExistException";
    static final String PROJECT_RESPONSE_SHOULD_BE_EQUAL_TO_EXPECTED = "ProjectResponse should be equal to expected";
    @InjectMocks
    private ProjectServiceImpl projectService;

    @Mock
    private ProjectMemberService projectMemberService;

    @Mock
    private ProjectMemberRepository projectMemberRepository;

    @Mock
    private ProjectRepository projectRepository;

    private static final AddNewProjectData ADD_PROJECT_DATA = ProjectTestDataBuilder.addNewProjectData();

    private final ProjectMapper MAPPER = Mappers.getMapper(ProjectMapper.class);

    @Mock
    private ProjectMapper projectMapper;

    @Test
    final void test_addNewProject_shouldAddNewProject() {
        // given
        final long totalNumberOfMembers = 0L;
        ProjectInfoResponse expected = MAPPER.toProjectInfoResponse(ADD_PROJECT_DATA.project(), Collections.emptyList(), totalNumberOfMembers);

        // when
        when(projectRepository.save(ADD_PROJECT_DATA.project()))
                .thenReturn(ADD_PROJECT_DATA.project());
        when(projectMemberService.getProjectMembersInfo(ADD_PROJECT_DATA.project().getProjectId()))
                .thenReturn(Collections.emptyList());
        when(projectMapper.toProjectInfoResponse(ADD_PROJECT_DATA.project(), Collections.emptyList(), totalNumberOfMembers))
                .thenReturn(expected);
        when(projectMemberRepository.save(ProjectMember.builder().build()))
                .thenReturn(ProjectMember.builder().build());

        ProjectInfoResponse actual = projectService
                .addNewProject(ADD_PROJECT_DATA.project().getName(), ADD_PROJECT_DATA.owner());

        // then
        assertEquals(expected, actual, PROJECT_RESPONSE_SHOULD_BE_EQUAL_TO_EXPECTED);
        verify(projectRepository).save(ADD_PROJECT_DATA.project());
    }

    @Test
    final void test_getProjectsOnPage_shouldGetOneElementListOfProjects() {
        // given
        final long totalNumberOfMembers = 1L;
        final int page = 0;
        Pageable pageable = PageRequest.of(page, 20);
        List<ProjectMemberInfoResponse> members = List.of(new ProjectMemberInfoResponse("full Name", 1L));

        ProjectInfoResponse response = MAPPER.toProjectInfoResponse(ADD_PROJECT_DATA.project(), members, totalNumberOfMembers);
        List<ProjectInfoResponse> expected = List.of(response);

        // when
        when(projectRepository.findAllByOwnerOrderByName(ADD_PROJECT_DATA.owner(), pageable))
                .thenReturn(new PageImpl<>(List.of(ADD_PROJECT_DATA.project())));
        when(projectMemberService.getTotalNumberOfMembers(ADD_PROJECT_DATA.project().getProjectId()))
                .thenReturn(totalNumberOfMembers);
        when(projectMemberService.getProjectMembersInfo(ADD_PROJECT_DATA.project().getProjectId()))
                .thenReturn(members);
        when(projectMapper.toProjectInfoResponse(ADD_PROJECT_DATA.project(), members, totalNumberOfMembers))
                .thenReturn(response);

        List<ProjectInfoResponse> actual = projectService.getProjectsOnPage(page, ADD_PROJECT_DATA.owner());

        // then
        assertEquals(expected, actual, "List of ProjectResponse should be empty");
    }

    @Test
    final void test_updateProjectName_shouldThrowProjectDoesNotExistException() {
        // given
        final String newName = "newName";
        final long projectId = 1L;

        // when
        when(projectRepository.findByProjectIdAndOwner(projectId, ADD_PROJECT_DATA.owner()))
                .thenReturn(Optional.empty());

        // then
        assertThrows(ProjectDoesNotExistException.class,
                () -> projectService.updateProjectsName(newName, projectId, ADD_PROJECT_DATA.owner()),
                SHOULD_THROW_PROJECT_DOES_NOT_EXIST_EXCEPTION);
    }

    @Test
    final void test_updateProjectName_shouldUpdateProjectsName() {
        // given
        final String newName = "newName";
        final long projectId = 1L;
        ProjectResponse expected = MAPPER.toProjectResponse(ADD_PROJECT_DATA.project());

        // when
        when(projectRepository.findByProjectIdAndOwner(projectId, ADD_PROJECT_DATA.owner()))
                .thenReturn(Optional.of(ADD_PROJECT_DATA.project()));

        when(projectRepository.save(ADD_PROJECT_DATA.project()))
                .thenReturn(ADD_PROJECT_DATA.project());

        when(projectMapper.toProjectResponse(ADD_PROJECT_DATA.project()))
                .thenReturn(expected);

        ProjectResponse actual = projectService.updateProjectsName(newName, projectId, ADD_PROJECT_DATA.owner());

        // then
        assertEquals(expected, actual, PROJECT_RESPONSE_SHOULD_BE_EQUAL_TO_EXPECTED);
    }

    @Test
    final void test_deleteProject_shouldThrowProjectDoesNotExistException() {
        // given
        final long projectId = 1L;

        // when
        when(projectRepository.findByProjectIdAndOwner(projectId, ADD_PROJECT_DATA.owner()))
                .thenReturn(Optional.empty());

        // then
        assertThrows(ProjectDoesNotExistException.class,
                () -> projectService.deleteProject(projectId, ADD_PROJECT_DATA.owner()),
                SHOULD_THROW_PROJECT_DOES_NOT_EXIST_EXCEPTION);
    }

    @Test
    final void test_deleteProject_shouldDeleteProject() {
        // given
        final long projectId = 1L;
        ProjectResponse expected = MAPPER.toProjectResponse(ADD_PROJECT_DATA.project());

        // when
        when(projectRepository.findByProjectIdAndOwner(projectId, ADD_PROJECT_DATA.owner()))
                .thenReturn(Optional.of(ADD_PROJECT_DATA.project()));

        when(projectMapper.toProjectResponse(ADD_PROJECT_DATA.project()))
                .thenReturn(expected);

        ProjectResponse actual = projectService.deleteProject(projectId, ADD_PROJECT_DATA.owner());

        // then
        assertEquals(expected, actual, PROJECT_RESPONSE_SHOULD_BE_EQUAL_TO_EXPECTED);
    }
}
