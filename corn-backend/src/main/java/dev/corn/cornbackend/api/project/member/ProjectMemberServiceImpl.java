package dev.corn.cornbackend.api.project.member;

import dev.corn.cornbackend.api.project.member.data.ProjectMemberInfoResponse;
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
import dev.corn.cornbackend.utils.exceptions.project.member.InvalidUsernameException;
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

    private static final String PROJECT_NOT_FOUND = "Project with project id: %d does not exist";

    @Override
    public final ProjectMemberResponse addMemberToProject(String username, long projectId, User user) {

        log.info("Adding member to project with username: {} and projectId: {}", username, projectId);

        User userToAdd = getUserFromRepository(username);
        Project project = getProjectFromRepositoryIfOwner(projectId, user);

        if(projectRepository.existsByProjectMemberAndProjectId(userToAdd, projectId)) {
            throw new InvalidUsernameException(String.format("User: %s is already owner or member of this project", username));
        }

        log.info("Found user: {} and project: {}", userToAdd, project);

        ProjectMember projectMember = ProjectMember
                .builder()
                .user(userToAdd)
                .project(project)
                .backlogItems(Collections.emptyList())
                .build();
        log.info("Created projectMember: {}", projectMember);

        ProjectMember newMember = projectMemberRepository.save(projectMember);

        return projectMemberMapper.toProjectMemberResponse(newMember);
    }

    @Override
    public final List<ProjectMemberResponse> getProjectMembers(long projectId, int page, User user) {
        Pageable pageable = PageRequest.of(page, MEMBERS_PAGE_SIZE);

        log.info("Getting project members for projectId: {}", projectId);

        Project project = getProjectFromRepositoryIfOwnerOrMember(projectId, user);

        log.info("Found project: {}", project);

        Page<ProjectMember> projectMembers = projectMemberRepository.findAllByProject(project, pageable);

        log.info("Found projectMembers: {}", projectMembers.getTotalElements());

        return projectMembers
                .stream()
                .map(projectMemberMapper::toProjectMemberResponse)
                .toList();
    }

    @Override
    public final ProjectMemberResponse removeMemberFromProject(String username, long projectId, User user) {

        if(username.equals(user.getUsername())) {
            throw new InvalidUsernameException("You cannot remove yourself from the project");
        }

        log.info("Removing member from project with username: {} and projectId: {}", username, projectId);

        User userToRemove = getUserFromRepository(username);
        ProjectMember projectMember = projectMemberRepository
                .findByProjectAndUser(getProjectFromRepositoryIfOwner(projectId, user), userToRemove)
                .orElseThrow(() -> new ProjectMemberDoesNotExistException(String.format("Project member of id username %s in project %s does not exist", username, projectId))
                );
        log.info("Found projectMember: {}", projectMember);

        projectMemberRepository.deleteById(projectMember.getProjectMemberId());

        return projectMemberMapper.toProjectMemberResponse(projectMember);
    }

    @Override
    public final List<ProjectMemberInfoResponse> getProjectMembersInfo(long projectId) {
        Pageable pageable = PageRequest.of(0, 5);

        log.info("Getting project members info for projectId: {}", projectId);

        Page<ProjectMember> members = projectMemberRepository.findMembersByProjectIdForProjectInfo(projectId, pageable);

        log.info("Found members: {}", members);

        return members
                .stream()
                .map(ProjectMember::getUser)
                .map(user -> ProjectMemberInfoResponse
                        .builder()
                        .fullName(user.getFullName())
                        .userId(user.getUserId())
                        .build()
                )
                .toList();
    }

    @Override
    public final long getTotalNumberOfMembers(long projectId) {
        log.info("Getting total number of members for projectId: {}", projectId);

        long totalNumberOfMembers = projectMemberRepository.countByProjectProjectId(projectId);

        log.info("Total number of members: {}", totalNumberOfMembers);

        return totalNumberOfMembers;
    }

    private User getUserFromRepository(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserDoesNotExistException(HttpStatus.NOT_FOUND,
                        String.format("User with username: %s does not exist", username))
                );
    }

    private Project getProjectFromRepositoryIfOwner(long projectId, User user) {
        return projectRepository.findByProjectIdAndOwner(projectId, user)
                .orElseThrow(() -> new ProjectDoesNotExistException(String.format(PROJECT_NOT_FOUND, projectId))
                );
    }

    private Project getProjectFromRepositoryIfOwnerOrMember(long projectId, User user) {
        return projectRepository.findByIdWithProjectMember(projectId, user)
                .orElseThrow(() -> new ProjectDoesNotExistException(String.format(PROJECT_NOT_FOUND, projectId))
                );
    }
}
