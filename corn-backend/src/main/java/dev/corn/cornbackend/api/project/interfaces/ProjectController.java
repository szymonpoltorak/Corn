package dev.corn.cornbackend.api.project.interfaces;

import dev.corn.cornbackend.api.project.data.ProjectResponse;
import dev.corn.cornbackend.entities.user.User;

import java.util.List;

public interface ProjectController {
    ProjectResponse addNewProject(String name, User user);

    List<ProjectResponse> getProjectsOnPage(int page, User user);

    ProjectResponse updateProjectsName(String name, long projectId, User user);

    ProjectResponse deleteProject(long projectId, User user);
}
