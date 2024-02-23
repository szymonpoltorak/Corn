package dev.corn.cornbackend.entities.project.interfaces;

import dev.corn.cornbackend.api.project.data.ProjectInfoResponse;
import dev.corn.cornbackend.api.project.data.ProjectResponse;
import dev.corn.cornbackend.api.project.member.data.ProjectMemberInfoResponse;
import dev.corn.cornbackend.entities.project.Project;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper for Project
 */
@Mapper(componentModel = "spring")
public interface ProjectMapper {

    /**
     * Map a project to a project response
     *
     * @param project the project to map
     * @return the project response
     */
    ProjectResponse toProjectResponse(Project project);

    /**
     * Map a project to a project info response
     *
     * @param project the project to map
     * @param membersInfo the members info of the project
     * @return the project info response
     */
    ProjectInfoResponse toProjectInfoResponse(Project project, List<ProjectMemberInfoResponse> membersInfo,
                                              long totalNumberOfUsers);
}
