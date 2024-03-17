package dev.corn.cornbackend.entities.backlog.item.interfaces;

import dev.corn.cornbackend.api.backlog.item.data.BacklogItemDetails;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemResponse;
import dev.corn.cornbackend.entities.backlog.item.BacklogItem;
import dev.corn.cornbackend.entities.project.member.ProjectMember;
import dev.corn.cornbackend.entities.project.member.ProjectMemberMapperImpl;
import dev.corn.cornbackend.entities.project.member.interfaces.ProjectMemberMapper;
import dev.corn.cornbackend.entities.sprint.interfaces.SprintMapper;
import dev.corn.cornbackend.entities.user.data.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity BacklogItem and its DTO BacklogItemResponse.
 */
@Mapper(componentModel = "spring", uses = SprintMapper.class)
public interface BacklogItemMapper {
    /**
     * Maps a BacklogItem to a BacklogItemResponse.
     *
     * @param backlogItem The BacklogItem to map.
     * @return The mapped BacklogItemResponse.
     */
    @Mapping(target="projectId", expression="java(backlogItem.getProject().getProjectId())")
    @Mapping(target="sprintId", expression="java(backlogItem.getSprint().getSprintId())")
    BacklogItemResponse backlogItemToBacklogItemResponse(BacklogItem backlogItem);

    /**
     * Maps a BacklogItem to a BacklogItemDetails.
     *
     * @param backlogItem The BacklogItem to map.
     * @return The mapped BacklogItemDetails.
     */
    @Mapping(target = "sprint", source = "sprint")
    BacklogItemDetails backlogItemToBacklogItemDetails(BacklogItem backlogItem);

    /**
     * Maps a ProjectMember to a UserResponse.
     *
     * @param assignee The ProjectMember to map.
     * @return The mapped UserResponse.
     */
    default UserResponse mapProjectMemberToUserResponse(ProjectMember assignee) {
        ProjectMemberMapper projectMemberMapper = new ProjectMemberMapperImpl();

        return projectMemberMapper.mapProjectMememberToUserResponse(assignee);
    }
}
