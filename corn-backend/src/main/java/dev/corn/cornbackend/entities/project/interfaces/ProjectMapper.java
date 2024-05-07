package dev.corn.cornbackend.entities.project.interfaces;

import dev.corn.cornbackend.api.project.data.ProjectInfoResponse;
import dev.corn.cornbackend.api.project.data.ProjectResponse;
import dev.corn.cornbackend.api.sprint.data.SprintResponse;
import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.sprint.Sprint;
import dev.corn.cornbackend.entities.sprint.SprintMapperImpl;
import dev.corn.cornbackend.entities.sprint.interfaces.SprintMapper;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.entities.user.data.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
    @Mapping(target = "isOwner", expression = "java(user.equals(project.getOwner()))")
    @Mapping(target = "name", source = "project.name")
    ProjectInfoResponse toProjectInfoResponse(Project project, List<UserResponse> membersInfo,
                                              long totalNumberOfUsers, User user);

    default SprintResponse sprintToSprintResponse(Sprint sprint) {
        SprintMapper sprintMapper = new SprintMapperImpl();

        return sprintMapper.toSprintResponse(sprint);
    }
}
