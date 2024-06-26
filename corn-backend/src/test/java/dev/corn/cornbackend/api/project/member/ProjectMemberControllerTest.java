package dev.corn.cornbackend.api.project.member;

import dev.corn.cornbackend.api.project.member.data.ProjectMemberInfoExtendedResponse;
import dev.corn.cornbackend.api.project.member.data.ProjectMemberList;
import dev.corn.cornbackend.api.project.member.interfaces.ProjectMemberService;
import dev.corn.cornbackend.entities.project.member.ProjectMember;
import dev.corn.cornbackend.entities.project.member.ProjectMemberMapperImpl;
import dev.corn.cornbackend.entities.project.member.interfaces.ProjectMemberMapper;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.entities.user.data.UserResponse;
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
    final void test_addMemberToProject_shouldAddMemberToProject_whenCalledByOwner() {
        // given
        User user = ADD_NEW_PROJECT_DATA.owner();
        long projectId = ADD_NEW_PROJECT_DATA.project().getProjectId();
        ProjectMember projectMember = ProjectMember
                .builder()
                .user(user)
                .project(ADD_NEW_PROJECT_DATA.project())
                .build();
        UserResponse expected = MAPPER.mapProjectMememberToUserResponse(projectMember);


        // when
        when(projectMemberService.addMemberToProject(user.getUsername(), projectId, user))
                .thenReturn(expected);

        UserResponse actual = projectMemberController.addMemberToProject(user.getUsername(), projectId, user);

        // then
        assertEquals(expected, actual, "Should add assignee to project");
    }

    @Test
    final void test_getProjectMembers_shouldGetProjectMembers_whenCalledByOwner() {
        // given
        User user = ADD_NEW_PROJECT_DATA.owner();
        long projectId = ADD_NEW_PROJECT_DATA.project().getProjectId();
        ProjectMember projectMember = ProjectMember
                .builder()
                .user(user)
                .project(ADD_NEW_PROJECT_DATA.project())
                .build();
        ProjectMemberList expected = new ProjectMemberList(List.of(MAPPER.mapProjectMememberToUserResponse(projectMember)), 0L);

        // when
        when(projectMemberService.getProjectMembers(projectId, 0, user))
                .thenReturn(expected);

        ProjectMemberList actual = projectMemberController.getProjectMembers(projectId, 0, user);

        // then
        assertEquals(expected, actual, "Should add assignee to project");
    }

    @Test
    final void test_removeMemberFromProject_shouldAddMemberToProject_whenCalledByOwner() {
        // given
        User user = ADD_NEW_PROJECT_DATA.owner();
        long projectId = ADD_NEW_PROJECT_DATA.project().getProjectId();
        ProjectMember projectMember = ProjectMember
                .builder()
                .user(user)
                .project(ADD_NEW_PROJECT_DATA.project())
                .build();
        UserResponse expected = MAPPER.mapProjectMememberToUserResponse(projectMember);

        // when
        when(projectMemberService.removeMemberFromProject(user.getUsername(), projectId, user))
                .thenReturn(expected);

        UserResponse actual = projectMemberController.removeMemberFromProject(user.getUsername(), projectId, user);

        // then
        assertEquals(expected, actual, "Should add assignee to project");
    }

    @Test
    final void test_getProjectMemberId_shouldAddMemberToProject_whenCalledByOwner() {
        // given
        User user = ADD_NEW_PROJECT_DATA.owner();
        long projectId = ADD_NEW_PROJECT_DATA.project().getProjectId();
        ProjectMember projectMember = ProjectMember
                .builder()
                .user(user)
                .project(ADD_NEW_PROJECT_DATA.project())
                .build();
        ProjectMemberInfoExtendedResponse expected =
                ProjectMemberInfoExtendedResponse.fromProjectMember(projectMember);

        // when
        when(projectMemberService.getProjectMemberId(projectId, user))
                .thenReturn(expected);

        ProjectMemberInfoExtendedResponse actual = projectMemberController.getProjectMemberId(projectId, user);

        // then
        assertEquals(expected, actual, "Should return project memebr id response");
    }

    @Test
    final void test_getAllProjectMembers_shouldAddMemberToProject_whenCalledByOwner() {
        // given
        User user = ADD_NEW_PROJECT_DATA.owner();
        long projectId = ADD_NEW_PROJECT_DATA.project().getProjectId();
        ProjectMember projectMember = ProjectMember
                .builder()
                .user(user)
                .project(ADD_NEW_PROJECT_DATA.project())
                .build();
        List<ProjectMemberInfoExtendedResponse> expected =
                List.of(ProjectMemberInfoExtendedResponse.fromProjectMember(projectMember));

        // when
        when(projectMemberService.getAllProjectMembers(projectId, user))
                .thenReturn(expected);

        List<ProjectMemberInfoExtendedResponse> actual = projectMemberController.getAllProjectMembers(projectId, user);

        // then
        assertEquals(expected, actual, "Should return list with all project members");
    }

}
