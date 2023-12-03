package dev.corn.cornbackend.api.project;

import dev.corn.cornbackend.api.project.data.ProjectResponse;
import dev.corn.cornbackend.api.project.interfaces.ProjectController;
import dev.corn.cornbackend.api.project.interfaces.ProjectService;
import dev.corn.cornbackend.entities.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static dev.corn.cornbackend.api.project.constants.ProjectMappings.ADD_PROJECT;
import static dev.corn.cornbackend.api.project.constants.ProjectMappings.DELETE_PROJECT;
import static dev.corn.cornbackend.api.project.constants.ProjectMappings.GET_PROJECTS_ON_PAGE;
import static dev.corn.cornbackend.api.project.constants.ProjectMappings.PROJECT_API_ENDPOINT;
import static dev.corn.cornbackend.api.project.constants.ProjectMappings.UPDATE_PROJECTS_NAME;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = PROJECT_API_ENDPOINT)
public class ProjectControllerImpl implements ProjectController {
    private final ProjectService projectService;

    @Override
    @PostMapping(value = ADD_PROJECT)
    @ResponseStatus(value = HttpStatus.CREATED)
    public final ProjectResponse addNewProject(@RequestParam String name,
                                               @AuthenticationPrincipal User user) {
        return projectService.addNewProject(name, user);
    }

    @Override
    @GetMapping(value = GET_PROJECTS_ON_PAGE)
    public final List<ProjectResponse> getProjectsOnPage(@RequestParam int page,
                                                         @AuthenticationPrincipal User user) {
        return projectService.getProjectsOnPage(page, user);
    }

    @Override
    @PatchMapping(value = UPDATE_PROJECTS_NAME)
    public final ProjectResponse updateProjectsName(@RequestParam String name, @RequestParam long projectId) {
        return projectService.updateProjectsName(name, projectId);
    }

    @Override
    @DeleteMapping(value = DELETE_PROJECT)
    public final ProjectResponse deleteProject(@RequestParam long projectId) {
        return projectService.deleteProject(projectId);
    }
}
