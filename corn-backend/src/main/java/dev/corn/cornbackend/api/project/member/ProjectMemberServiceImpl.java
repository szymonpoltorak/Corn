package dev.corn.cornbackend.api.project.member;

import dev.corn.cornbackend.api.project.member.data.ProjectMemberResponse;
import dev.corn.cornbackend.api.project.member.interfaces.ProjectMemberService;
import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.project.interfaces.ProjectRepository;
import dev.corn.cornbackend.entities.project.member.ProjectMember;
import dev.corn.cornbackend.entities.project.member.interfaces.ProjectMemberMapper;
import dev.corn.cornbackend.entities.project.member.interfaces.ProjectMemberRepository;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.entities.user.interfaces.UserRepository;
import dev.corn.cornbackend.utils.exceptions.project.ProjectDoesNotExistException;
import dev.corn.cornbackend.utils.exceptions.project.member.ProjectMemberDoesNotExistException;
import dev.corn.cornbackend.utils.exceptions.user.UserDoesNotExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectMemberServiceImpl implements ProjectMemberService {
    private static final int MEMBERS_PAGE_SIZE = 20;
    private final ProjectMemberRepository projectMemberRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMemberMapper projectMemberMapper;

    @Override
    public final ProjectMemberResponse addMemberToProject(String username, long projectId) {
        log.info("Adding member to project with username: {} and projectId: {}", username, projectId);

        User user = getUserFromRepository(username);
        Project project = getProjectFromRepository(projectId);

        log.info("Found user: {} and project: {}", user, project);

        ProjectMember projectMember = ProjectMember
                .builder()
                .user(user)
                .project(project)
                .backlogItems(Collections.emptyList())
                .build();
        log.info("Created projectMember: {}", projectMember);

        ProjectMember newMember = projectMemberRepository.save(projectMember);

        return projectMemberMapper.toProjectMemberResponse(newMember);
    }

    @Override
    public final List<ProjectMemberResponse> getProjectMembers(long projectId, int page) {
        Pageable pageable = PageRequest.of(page, MEMBERS_PAGE_SIZE);

        log.info("Getting project members for projectId: {}", projectId);

        Project project = getProjectFromRepository(projectId);

        log.info("Found project: {}", project);

        Page<ProjectMember> projectMembers = projectMemberRepository.findAllByProject(project, pageable);

        log.info("Found projectMembers: {}", projectMembers.getTotalElements());

        return projectMembers
                .stream()
                .map(projectMemberMapper::toProjectMemberResponse)
                .toList();
    }

    @Override
    public final ProjectMemberResponse removeMemberFromProject(String username, long projectId) {
        log.info("Removing member from project with username: {} and projectId: {}", username, projectId);

        User user = getUserFromRepository(username);
        ProjectMember projectMember = projectMemberRepository
                .findByProjectAndUser(getProjectFromRepository(projectId), user)
                .orElseThrow(() -> new ProjectMemberDoesNotExistException(String.format("Project member of id username %s in project %s does not exist", username, projectId))
                );
        log.info("Found projectMember: {}", projectMember);

        projectMemberRepository.deleteById(projectMember.getProjectMemberId());

        return projectMemberMapper.toProjectMemberResponse(projectMember);
    }

    private User getUserFromRepository(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserDoesNotExistException(HttpStatus.NOT_FOUND,
                        String.format("User with username: %s does not exist", username))
                );
    }

    private Project getProjectFromRepository(long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectDoesNotExistException(String.format("Project with projectId: %d does not exist", projectId))
                );
    }
}
