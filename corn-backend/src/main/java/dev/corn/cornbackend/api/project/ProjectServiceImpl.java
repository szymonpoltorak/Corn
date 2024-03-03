package dev.corn.cornbackend.api.project;

import dev.corn.cornbackend.api.project.data.ProjectInfoResponse;
import dev.corn.cornbackend.api.project.data.ProjectResponse;
import dev.corn.cornbackend.api.project.interfaces.ProjectService;
import dev.corn.cornbackend.api.project.member.data.ProjectMemberInfoResponse;
import dev.corn.cornbackend.api.project.member.interfaces.ProjectMemberService;
import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.project.interfaces.ProjectMapper;
import dev.corn.cornbackend.entities.project.interfaces.ProjectRepository;
import dev.corn.cornbackend.entities.project.member.ProjectMember;
import dev.corn.cornbackend.entities.project.member.interfaces.ProjectMemberRepository;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.utils.exceptions.project.ProjectDoesNotExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private static final int PROJECTS_PER_PAGE = 20;
    private static final long NEW_PROJECT_MEMBER_NUMBER = 0L;
    private static final String PROJECT_DOES_NOT_EXIST = "Project does not exist!";
    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectMapper projectMapper;
    private final ProjectMemberService projectMemberService;

    @Override
    public final ProjectInfoResponse addNewProject(String name, User user) {
        log.info("Adding new project with name: {} and user: {}", name, user);

        Project project = Project
                .builder()
                .name(name)
                .owner(user)
                .sprints(Collections.emptyList())
                .build();

        Project newProject = projectRepository.save(project);

        log.info("Created project: {}", newProject);

        ProjectMember projectMember = ProjectMember
                .builder()
                .user(user)
                .project(newProject)
                .backlogItems(Collections.emptyList())
                .build();

        ProjectMember newProjectMember = projectMemberRepository.save(projectMember);

        log.info("Created project member: {}", newProjectMember);

        return mapToProjectInfoResponse(newProject, NEW_PROJECT_MEMBER_NUMBER);
    }

    @Override
    public final List<ProjectInfoResponse> getProjectsOnPage(int page, User user) {
        log.info("Getting projects on page: {} for user: {}", page, user);

        Pageable pageable = PageRequest.of(page, PROJECTS_PER_PAGE);
        Page<Project> projects = projectRepository.findAllByOwnerOrderByName(user, pageable);

        log.info("Projects found on page : {}", projects.getTotalElements());

        return projects
                .stream()
                .map(this::mapToProjectInfoResponse)
                .toList();
    }

    @Override
    public final ProjectResponse updateProjectsName(String name, long projectId, User user) {
        log.info("Updating project with id: {} and name: {}", projectId, name);

        Project projectToUpdate = projectRepository.findByProjectIdAndOwner(projectId, user)
                .orElseThrow(() -> new ProjectDoesNotExistException(PROJECT_DOES_NOT_EXIST));

        log.info("Found project to update: {}", projectToUpdate);

        projectToUpdate.setName(name);

        Project updatedProject = projectRepository.save(projectToUpdate);

        log.info("Updated project: {}", updatedProject);

        return projectMapper.toProjectResponse(updatedProject);
    }

    @Override
    public final ProjectResponse deleteProject(long projectId, User user) {
        log.info("Deleting project with id: {}", projectId);

        Project projectToDelete = projectRepository.findByProjectIdAndOwner(projectId, user)
                .orElseThrow(() -> new ProjectDoesNotExistException(PROJECT_DOES_NOT_EXIST));

        log.info("Found project to delete: {}", projectToDelete);

        projectRepository.deleteById(projectId);

        return projectMapper.toProjectResponse(projectToDelete);
    }

    private ProjectInfoResponse mapToProjectInfoResponse(Project project) {
        long totalNumberOfUsers = projectMemberService.getTotalNumberOfMembers(project.getProjectId());

        return mapToProjectInfoResponse(project, totalNumberOfUsers);
    }

    private ProjectInfoResponse mapToProjectInfoResponse(Project project, long totalNumberOfUsers) {
        List<ProjectMemberInfoResponse> members = projectMemberService.getProjectMembersInfo(project.getProjectId());

        return projectMapper.toProjectInfoResponse(project, members, totalNumberOfUsers);
    }
}
