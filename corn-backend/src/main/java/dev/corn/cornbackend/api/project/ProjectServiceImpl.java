package dev.corn.cornbackend.api.project;

import dev.corn.cornbackend.api.project.data.ProjectResponse;
import dev.corn.cornbackend.api.project.interfaces.ProjectService;
import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.project.interfaces.ProjectMapper;
import dev.corn.cornbackend.entities.project.interfaces.ProjectRepository;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.utils.exceptions.project.ProjectDoesNotExistException;
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
public class ProjectServiceImpl implements ProjectService {
    private static final int PROJECTS_PER_PAGE = 20;
    private static final String PROJECT_DOES_NOT_EXIST = "Project does not exist!";
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Override
    public final ProjectResponse addNewProject(String name, User user) {
        log.info("Adding new project with name: {} and user: {}", name, user);

        Project project = Project
                .builder()
                .name(name)
                .owner(user)
                .sprints(Collections.emptyList())
                .build();
        log.info("Created project: {}", project);

        Project newProject = projectRepository.save(project);

        log.info("Saved project: {}", newProject);

        return projectMapper.toProjectResponse(newProject);
    }

    @Override
    public final List<ProjectResponse> getProjectsOnPage(int page, User user) {
        log.info("Getting projects on page: {} for user: {}", page, user);

        Pageable pageable = PageRequest.of(page, PROJECTS_PER_PAGE);
        Page<Project> projects = projectRepository.findAllByOwnerOrderByName(user, pageable);

        log.info("Projects found on page : {}", projects.getTotalElements());

        return projects
                .stream()
                .map(projectMapper::toProjectResponse)
                .toList();
    }

    @Override
    public final ProjectResponse updateProjectsName(String name, long projectId) {
        log.info("Updating project with id: {} and name: {}", projectId, name);

        Project projectToUpdate = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectDoesNotExistException(HttpStatus.NOT_FOUND, PROJECT_DOES_NOT_EXIST));

        log.info("Found project to update: {}", projectToUpdate);

        projectToUpdate.setName(name);

        Project updatedProject = projectRepository.save(projectToUpdate);

        log.info("Updated project: {}", updatedProject);

        return projectMapper.toProjectResponse(updatedProject);
    }

    @Override
    public final ProjectResponse deleteProject(long projectId) {
        log.info("Deleting project with id: {}", projectId);

        Project projectToDelete = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectDoesNotExistException(HttpStatus.NOT_FOUND, PROJECT_DOES_NOT_EXIST));

        log.info("Found project to delete: {}", projectToDelete);

        projectRepository.deleteById(projectId);

        return projectMapper.toProjectResponse(projectToDelete);
    }
}
