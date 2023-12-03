package dev.corn.cornbackend.api.project.member;

import dev.corn.cornbackend.api.project.member.data.ProjectMemberResponse;
import dev.corn.cornbackend.api.project.member.interfaces.ProjectMemberService;
import dev.corn.cornbackend.entities.project.member.ProjectMember;
import dev.corn.cornbackend.entities.project.member.ProjectMemberMapperImpl;
import dev.corn.cornbackend.entities.project.member.interfaces.ProjectMemberMapper;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.test.project.ProjectTestDataBuilder;
import dev.corn.cornbackend.test.project.data.AddNewProjectData;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ProjectMemberService.class)
class ProjectMemberControllerTest {
    @InjectMocks
    private ProjectMemberControllerImpl projectMemberController;

    @Mock
    private ProjectMemberService projectMemberService;

    private static final ProjectMemberMapper MAPPER = new ProjectMemberMapperImpl();

    private static final AddNewProjectData ADD_NEW_PROJECT_DATA = ProjectTestDataBuilder.addNewProjectData();


    @Test
    final void test_addMemberToProject_shouldAddMemberToProject() {
        // given
        User user = ADD_NEW_PROJECT_DATA.owner();
        long projectId = ADD_NEW_PROJECT_DATA.project().getProjectId();
        ProjectMember projectMember = ProjectMember
                .builder()
                .user(user)
                .project(ADD_NEW_PROJECT_DATA.project())
                .build();
        ProjectMemberResponse expected = MAPPER.toProjectMemberResponse(projectMember);


        // when
        when(projectMemberService.addMemberToProject(user.getUsername(), projectId))
                .thenReturn(expected);

        ProjectMemberResponse actual = projectMemberController.addMemberToProject(user.getUsername(), projectId);

        // then
        assertEquals(expected, actual, "Should add member to project");
    }

    @Test
    final void test_getProjectMembers_shouldAddMemberToProject() {
        // given
        User user = ADD_NEW_PROJECT_DATA.owner();
        long projectId = ADD_NEW_PROJECT_DATA.project().getProjectId();
        ProjectMember projectMember = ProjectMember
                .builder()
                .user(user)
                .project(ADD_NEW_PROJECT_DATA.project())
                .build();
        List<ProjectMemberResponse> expected = List.of(MAPPER.toProjectMemberResponse(projectMember));

        // when
        when(projectMemberService.getProjectMembers(projectId, 0))
                .thenReturn(expected);

        List<ProjectMemberResponse> actual = projectMemberController.getProjectMembers(projectId, 0);

        // then
        assertEquals(expected, actual, "Should add member to project");
    }

    @Test
    final void test_removeMemberFromProject_shouldAddMemberToProject() {
        // given
        User user = ADD_NEW_PROJECT_DATA.owner();
        long projectId = ADD_NEW_PROJECT_DATA.project().getProjectId();
        ProjectMember projectMember = ProjectMember
                .builder()
                .user(user)
                .project(ADD_NEW_PROJECT_DATA.project())
                .build();
        ProjectMemberResponse expected = MAPPER.toProjectMemberResponse(projectMember);

        // when
        when(projectMemberService.removeMemberFromProject(user.getUsername(), projectId))
                .thenReturn(expected);

        ProjectMemberResponse actual = projectMemberController.removeMemberFromProject(user.getUsername(), projectId);

        // then
        assertEquals(expected, actual, "Should add member to project");
    }
}