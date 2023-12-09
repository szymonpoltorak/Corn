package dev.corn.cornbackend.test.backlog.item;

import dev.corn.cornbackend.api.backlog.item.data.BacklogItemRequest;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemResponse;
import dev.corn.cornbackend.entities.backlog.item.BacklogItem;
import dev.corn.cornbackend.entities.backlog.item.ItemStatus;
import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.project.member.ProjectMember;
import dev.corn.cornbackend.entities.sprint.Sprint;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.test.backlog.item.data.AddBacklogItemTestData;
import dev.corn.cornbackend.test.backlog.item.data.EntityData;

import java.time.LocalDate;
import java.util.Collections;

public final class BacklogItemTestDataBuilder {

    public static AddBacklogItemTestData addBacklogItemTestData() {
        BacklogItem backlogItem = BacklogItem.builder()
                .backlogItemId(1L)
                .title("Title")
                .description("Description")
                .sprint(sprint())
                .project(project())
                .assignee(projectMember())
                .status(ItemStatus.TODO)
                .build();

        BacklogItemRequest backlogItemRequest = BacklogItemRequest.builder()
                .title(backlogItem.getTitle())
                .description(backlogItem.getDescription())
                .projectId(backlogItem.getProject().getProjectId())
                .projectMemberId(backlogItem.getAssignee().getProjectMemberId())
                .sprintId(backlogItem.getSprint().getSprintId())
                .build();

        BacklogItemResponse backlogItemResponse = BacklogItemResponse.builder()
                .title(backlogItem.getTitle())
                .description(backlogItem.getDescription())
                .status(backlogItem.getStatus().toString())
                .build();

        return new AddBacklogItemTestData(backlogItemRequest, backlogItemResponse, backlogItem);
    }

    public static EntityData entityData() {
        return new EntityData(projectMember(), sprint(), project());
    }

    private static Project project() {
        return Project.builder()
                .projectId(1L)
                .owner(new User())
                .sprints(Collections.emptyList())
                .name("Project")
                .build();
    }

    private static Sprint sprint() {
        return Sprint.builder()
                .sprintId(1L)
                .project(project())
                .sprintName("Sprint")
                .sprintDescription("Description")
                .sprintStartDate(LocalDate.now())
                .sprintEndDate(LocalDate.now().plusDays(2L))
                .build();
    }

    private static ProjectMember projectMember() {
        return ProjectMember.builder()
                .projectMemberId(1L)
                .user(new User())
                .project(project())
                .build();
    }




    private BacklogItemTestDataBuilder() {

    }
}
