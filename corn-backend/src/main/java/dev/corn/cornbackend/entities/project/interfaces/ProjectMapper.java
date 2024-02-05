package dev.corn.cornbackend.entities.project.interfaces;

import dev.corn.cornbackend.api.project.data.ProjectResponse;
import dev.corn.cornbackend.entities.project.Project;
import org.mapstruct.Mapper;

/**
 * Mapper for Project
 */
@Mapper(componentModel = "spring")
public interface ProjectMapper {
    /**
     * Maps a Project to a ProjectResponse
     * @param project the project to map
     * @return the mapped project
     */
    ProjectResponse toProjectResponse(Project project);

}
