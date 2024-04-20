package dev.corn.cornbackend.api.project.member;

import dev.corn.cornbackend.api.project.member.data.ProjectMemberInfoExtendedResponse;
import dev.corn.cornbackend.api.project.member.data.ProjectMemberList;
import dev.corn.cornbackend.entities.project.interfaces.ProjectRepository;
import dev.corn.cornbackend.entities.project.member.ProjectMember;
import dev.corn.cornbackend.entities.project.member.ProjectMemberMapperImpl;
import dev.corn.cornbackend.entities.project.member.interfaces.ProjectMemberMapper;
import dev.corn.cornbackend.entities.project.member.interfaces.ProjectMemberRepository;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.entities.user.data.UserResponse;
import dev.corn.cornbackend.entities.user.interfaces.UserRepository;
import dev.corn.cornbackend.test.project.ProjectTestDataBuilder;
import dev.corn.cornbackend.test.project.data.AddNewProjectData;
import dev.corn.cornbackend.utils.exceptions.project.ProjectDoesNotExistException;
import dev.corn.cornbackend.utils.exceptions.project.member.InvalidUsernameException;
import dev.corn.cornbackend.utils.exceptions.project.member.ProjectMemberDoesNotExistException;
import dev.corn.cornbackend.utils.exceptions.user.UserDoesNotExistException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {
        ProjectRepository.class,
        ProjectMemberRepository.class,
        UserRepository.class
})
class ProjectMemberServiceTest {
    private static final String SHOULD_THROW_EXCEPTION_WHEN_PROJECT_DOES_NOT_EXIST = "Should throw exception when project does not exist";
    @InjectMocks
    private ProjectMemberServiceImpl projectMemberService;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectMemberRepository projectMemberRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProjectMemberMapper projectMemberMapper;

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
        String username = "abababababababab";

        // when
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(user));
        when(projectRepository.findByProjectIdAndOwner(projectId, user))
                .thenReturn(Optional.of(ADD_NEW_PROJECT_DATA.project()));
        when(projectRepository.existsByProjectMemberAndProjectId(user, projectId))
                .thenReturn(false);
        when(projectMemberRepository.save(any(ProjectMember.class)))
                .thenReturn(projectMember);
        when(projectMemberMapper.mapProjectMememberToUserResponse(projectMember))
                .thenReturn(expected);

        UserResponse actual = projectMemberService
                .addMemberToProject(username, projectId, user);

        // then
        assertEquals(expected, actual, "Should add assignee to project");
        verify(projectMemberRepository).save(any(ProjectMember.class));
    }

    @Test
    final void test_addMemberToProject_shouldThrowExceptionUserNotExist() {
        // given
        User user = ADD_NEW_PROJECT_DATA.owner();
        long projectId = ADD_NEW_PROJECT_DATA.project().getProjectId();
        String username = "abababababababab";

        // when
        when(userRepository.findByUsername(user.getUsername()))
                .thenReturn(Optional.empty());

        // then
        assertThrows(UserDoesNotExistException.class, () -> projectMemberService
                        .addMemberToProject(username, projectId, user),
                "Should throw exception when user does not exist");
    }

    @Test
    final void test_addMemberToProject_shouldThrowExceptionProjectNotExist() {
        // given
        User user = ADD_NEW_PROJECT_DATA.owner();
        long projectId = ADD_NEW_PROJECT_DATA.project().getProjectId();
        String username = "abababababababab";

        // when
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(user));
        when(projectRepository.findByProjectIdAndOwner(projectId, user))
                .thenReturn(Optional.empty());

        // then
        assertThrows(ProjectDoesNotExistException.class, () -> projectMemberService
                        .addMemberToProject(username, projectId, user),
                SHOULD_THROW_EXCEPTION_WHEN_PROJECT_DOES_NOT_EXIST);
    }

    @Test
    final void test_addMemberToProject_shouldThrowExceptionWhenUserAlreadyOwnerOfProject() {
        //given
        User user = ADD_NEW_PROJECT_DATA.owner();
        long projectId = ADD_NEW_PROJECT_DATA.project().getProjectId();

        //when
        when(userRepository.findByUsername(user.getUsername()))
                .thenReturn(Optional.of(user));
        when(projectRepository.findByProjectIdAndOwner(projectId, user))
                .thenReturn(Optional.of(ADD_NEW_PROJECT_DATA.project()));
        when(projectRepository.existsByProjectMemberAndProjectId(user, projectId))
                .thenReturn(true);

        //then
        assertThrows(InvalidUsernameException.class, () -> projectMemberService
                .addMemberToProject(user.getUsername(), projectId, user),
                "Should throw exception if given username is username of project owner");
    }

    @Test
    final void test_getProjectMembers_shouldGetProjects() {
        // given
        int page = 0;
        long projectId = ADD_NEW_PROJECT_DATA.project().getProjectId();
        User user = ADD_NEW_PROJECT_DATA.owner();
        ProjectMember projectMember = ProjectMember
                .builder()
                .user(user)
                .project(ADD_NEW_PROJECT_DATA.project())
                .build();
        ProjectMemberList expected = new ProjectMemberList(List.of(MAPPER.mapProjectMememberToUserResponse(projectMember)), 0L);

        // when
        when(projectRepository.findByIdWithProjectMember(projectId, user))
                .thenReturn(Optional.of(ADD_NEW_PROJECT_DATA.project()));
        when(projectMemberRepository.findAllByProject(eq(ADD_NEW_PROJECT_DATA.project()), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(projectMember)));
        when(projectMemberMapper.mapProjectMememberToUserResponse(projectMember))
                .thenReturn(MAPPER.mapProjectMememberToUserResponse(projectMember));
        when(projectMemberRepository.countByProjectProjectId(ADD_NEW_PROJECT_DATA.project().getProjectId()))
                .thenReturn(0L);

        ProjectMemberList actual = projectMemberService.getProjectMembers(projectId, page, user);

        // then
        assertEquals(expected, actual, "Should get project members");
    }

    @Test
    final void test_getProjectMembers_shouldThrowExceptionProjectNotExist() {
        // given
        int page = 0;
        long projectId = ADD_NEW_PROJECT_DATA.project().getProjectId();
        User user = ADD_NEW_PROJECT_DATA.owner();

        // when
        when(projectRepository.findByIdWithProjectMember(projectId, user))
                .thenReturn(Optional.empty());

        // then
        assertThrows(ProjectDoesNotExistException.class, () -> projectMemberService
                        .getProjectMembers(projectId, page, user),
                SHOULD_THROW_EXCEPTION_WHEN_PROJECT_DOES_NOT_EXIST);
    }

    @Test
    final void test_removeMemberFromProject_shouldDeleteProject() {
        // given
        User user = ADD_NEW_PROJECT_DATA.owner();
        long projectId = ADD_NEW_PROJECT_DATA.project().getProjectId();
        ProjectMember projectMember = ProjectMember
                .builder()
                .user(user)
                .project(ADD_NEW_PROJECT_DATA.project())
                .build();
        UserResponse expected = MAPPER.mapProjectMememberToUserResponse(projectMember);
        String username = "abababababababab";

        // when
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(user));
        when(projectRepository.findByProjectIdAndOwner(projectId, user))
                .thenReturn(Optional.of(ADD_NEW_PROJECT_DATA.project()));
        when(projectMemberRepository.findByProjectAndUser(ADD_NEW_PROJECT_DATA.project(), user))
                .thenReturn(Optional.of(projectMember));
        when(projectMemberMapper.mapProjectMememberToUserResponse(projectMember))
                .thenReturn(expected);

        UserResponse actual = projectMemberService.removeMemberFromProject(username, projectId, user);

        // then
        assertEquals(expected, actual, "Should remove assignee from project");
        verify(projectMemberRepository).deleteById(projectMember.getProjectMemberId());
    }

    @Test
    final void test_removeMemberFromProject_shouldThrowExceptionProjectMemberDoesNotExist() {
        // given
        User user = ADD_NEW_PROJECT_DATA.owner();
        long projectId = ADD_NEW_PROJECT_DATA.project().getProjectId();
        String username = "abababababababab";

        // when
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(user));
        when(projectRepository.findByProjectIdAndOwner(projectId, user))
                .thenReturn(Optional.of(ADD_NEW_PROJECT_DATA.project()));
        when(projectMemberRepository.findByProjectAndUser(ADD_NEW_PROJECT_DATA.project(), user))
                .thenReturn(Optional.empty());

        // then
        assertThrows(ProjectMemberDoesNotExistException.class, () -> projectMemberService
                        .removeMemberFromProject(username, projectId, user),
                "Should throw exception when project assignee does not exist");
    }

    @Test
    final void test_removeMemberFromProject_shouldThrowExceptionUserDoesNotExist() {
        // given
        User user = ADD_NEW_PROJECT_DATA.owner();
        long projectId = ADD_NEW_PROJECT_DATA.project().getProjectId();
        String username = "abababababababab";

        // when
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.empty());

        // then
        assertThrows(UserDoesNotExistException.class, () -> projectMemberService
                        .removeMemberFromProject(username, projectId, user),
                "Should throw exception when project assignee does not exist");
    }

    @Test
    final void test_removeMemberFromProject_shouldThrowExceptionProjectDoesNotExist() {
        // given
        User user = ADD_NEW_PROJECT_DATA.owner();
        long projectId = ADD_NEW_PROJECT_DATA.project().getProjectId();
        String username = "abababababababab";

        // when
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(user));
        when(projectRepository.findByProjectIdAndOwner(projectId, user))
                .thenReturn(Optional.empty());

        // then
        assertThrows(ProjectDoesNotExistException.class, () -> projectMemberService
                        .removeMemberFromProject(username, projectId, user),
                "Should throw exception when project assignee does not exist");
    }

    @Test
    final void test_removeMemberFromProject_shouldThrowExceptionWhenUsernameEqualsUsersUsername() {
        //given
        User user = ADD_NEW_PROJECT_DATA.owner();
        long projectId = ADD_NEW_PROJECT_DATA.project().getProjectId();

        //when

        //then
        assertThrows(InvalidUsernameException.class, () -> projectMemberService
                .removeMemberFromProject(user.getUsername(), projectId, user));
    }

    @Test
    final void test_getProjectMemberId_shouldGetProjectMemberId() {
        // given
        long projectId = ADD_NEW_PROJECT_DATA.project().getProjectId();
        User user = ADD_NEW_PROJECT_DATA.owner();
        ProjectMember projectMember = ProjectMember
                .builder()
                .user(user)
                .project(ADD_NEW_PROJECT_DATA.project())
                .build();
        ProjectMemberInfoExtendedResponse expected = ProjectMemberInfoExtendedResponse.fromProjectMember(projectMember);

        // when
        when(projectRepository.findByIdWithProjectMember(projectId, user))
                .thenReturn(Optional.of(ADD_NEW_PROJECT_DATA.project()));
        when(projectMemberRepository.findByProjectAndUser(ADD_NEW_PROJECT_DATA.project(), user))
                .thenReturn(Optional.of(projectMember));

        ProjectMemberInfoExtendedResponse actual = projectMemberService.getProjectMemberId(projectId, user);

        // then
        assertEquals(expected, actual, "Should get all project members");
    }

    @Test
    final void test_getProjectMemberId_shouldThrowWhenProjectMemberDoesNotExist() {
        // given
        long projectId = ADD_NEW_PROJECT_DATA.project().getProjectId();
        User user = ADD_NEW_PROJECT_DATA.owner();

        // when
        when(projectRepository.findByIdWithProjectMember(projectId, user))
                .thenReturn(Optional.of(ADD_NEW_PROJECT_DATA.project()));
        when(projectMemberRepository.findByProjectAndUser(ADD_NEW_PROJECT_DATA.project(), user))
                .thenReturn(Optional.empty());

        // then
        assertThrows(ProjectMemberDoesNotExistException.class, () -> projectMemberService
                .getProjectMemberId(projectId, user));
    }

    @Test
    final void test_getAllProjectMembers_shouldGetAllMembers() {
        // given
        long projectId = ADD_NEW_PROJECT_DATA.project().getProjectId();
        Pageable wholePage = Pageable.unpaged();
        User user = ADD_NEW_PROJECT_DATA.owner();
        ProjectMember projectMember = ProjectMember
                .builder()
                .user(user)
                .project(ADD_NEW_PROJECT_DATA.project())
                .build();
        List<ProjectMemberInfoExtendedResponse> expected =
                List.of(ProjectMemberInfoExtendedResponse.fromProjectMember(projectMember));

        // when
        when(projectRepository.findByIdWithProjectMember(projectId, user))
                .thenReturn(Optional.of(ADD_NEW_PROJECT_DATA.project()));
        when(projectMemberRepository.findAllByProject(ADD_NEW_PROJECT_DATA.project(), wholePage))
                .thenReturn(new PageImpl<>(List.of(projectMember)));
        when(projectMemberMapper.mapProjectMememberToUserResponse(projectMember))
                .thenReturn(MAPPER.mapProjectMememberToUserResponse(projectMember));
        when(projectMemberRepository.countByProjectProjectId(ADD_NEW_PROJECT_DATA.project().getProjectId()))
                .thenReturn(0L);

        List<ProjectMemberInfoExtendedResponse> actual = projectMemberService.getAllProjectMembers(projectId, user);

        // then
        assertEquals(expected, actual, "Should get all project members");
    }

}
