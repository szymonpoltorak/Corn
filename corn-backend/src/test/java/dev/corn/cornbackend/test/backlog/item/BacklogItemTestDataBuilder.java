package dev.corn.cornbackend.test.backlog.item;

import dev.corn.cornbackend.api.backlog.item.data.BacklogItemDetails;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemRequest;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemResponse;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemResponseList;
import dev.corn.cornbackend.api.project.data.ProjectResponse;
import dev.corn.cornbackend.api.sprint.data.SprintResponse;
import dev.corn.cornbackend.entities.backlog.item.BacklogItem;
import dev.corn.cornbackend.entities.backlog.item.enums.ItemStatus;
import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.project.member.ProjectMember;
import dev.corn.cornbackend.entities.sprint.Sprint;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.entities.user.data.UserResponse;
import dev.corn.cornbackend.test.backlog.item.data.AddBacklogItemTestData;
import dev.corn.cornbackend.test.backlog.item.data.BacklogItemDetailsTestData;
import dev.corn.cornbackend.test.backlog.item.data.BacklogItemListTestData;
import dev.corn.cornbackend.test.backlog.item.data.EntityData;
import dev.corn.cornbackend.test.backlog.item.data.UpdateBacklogItemTestData;
import dev.corn.cornbackend.test.user.UserTestDataBuilder;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

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

        User user = UserTestDataBuilder.createSampleUser();

        return new AddBacklogItemTestData(backlogItemRequest, backlogItemResponse, backlogItem, user);
    }

    public static UpdateBacklogItemTestData updateBacklogItemTestData(ItemStatus itemStatus) {
        AddBacklogItemTestData addBacklogItemTestData = addBacklogItemTestData();
        BacklogItem backlogItem = addBacklogItemTestData.backLogItem();

        BacklogItem updatedBacklogItem = BacklogItem.builder()
                .backlogItemId(backlogItem.getBacklogItemId())
                .title("Updated Title")
                .description("Updated Description")
                .sprint(backlogItem.getSprint())
                .project(backlogItem.getProject())
                .assignee(backlogItem.getAssignee())
                .status(itemStatus)
                .build();

        BacklogItemRequest updateRequest = BacklogItemRequest.builder()
                .title(updatedBacklogItem.getTitle())
                .description(updatedBacklogItem.getDescription())
                .projectId(updatedBacklogItem.getProject().getProjectId())
                .projectMemberId(updatedBacklogItem.getAssignee().getProjectMemberId())
                .sprintId(updatedBacklogItem.getSprint().getSprintId())
                .itemStatus(itemStatus)
                .build();

        BacklogItemResponse updatedResponse = BacklogItemResponse.builder()
                .title(updatedBacklogItem.getTitle())
                .description(updatedBacklogItem.getDescription())
                .status(itemStatus.name())
                .taskFinishDate(itemStatus == ItemStatus.DONE ? LocalDate.now().toString() : null)
                .build();

        return new UpdateBacklogItemTestData(
                updateRequest,
                updatedResponse,
                backlogItem,
                updatedBacklogItem);
    }

    public static BacklogItemListTestData backlogItemListTestData() {
        BacklogItem backlogItem1 = BacklogItem.builder()
                .backlogItemId(1L)
                .title("Title1")
                .description("Description1")
                .sprint(sprint())
                .project(project())
                .assignee(projectMember())
                .status(ItemStatus.TODO)
                .build();

        BacklogItemResponse backlogItemResponse1 = BacklogItemResponse.builder()
                .title(backlogItem1.getTitle())
                .description(backlogItem1.getDescription())
                .status(backlogItem1.getStatus().toString())
                .build();

        BacklogItem backlogItem2 = BacklogItem.builder()
                .backlogItemId(2L)
                .title("Title2")
                .description("Description2")
                .sprint(sprint())
                .project(project())
                .assignee(projectMember())
                .status(ItemStatus.TODO)
                .build();

        BacklogItemResponse backlogItemResponse2 = BacklogItemResponse.builder()
                .title(backlogItem2.getTitle())
                .description(backlogItem2.getDescription())
                .status(backlogItem2.getStatus().toString())
                .build();

        return new BacklogItemListTestData(
                List.of(backlogItem1, backlogItem2),
                List.of(backlogItemResponse1, backlogItemResponse2),
                BacklogItemResponseList.builder()
                        .backlogItemResponseList(List.of(backlogItemResponse1, backlogItemResponse2))
                        .totalNumber(2L)
                        .build()
        );
    }

    public static BacklogItemDetailsTestData backlogItemDetailsTestData() {
        Sprint sprint = sprint();
        Project project = project();
        ProjectMember projectMember = projectMember();

        BacklogItem backlogItem = BacklogItem.builder()
                .backlogItemId(1L)
                .title("Title")
                .description("Description")
                .sprint(sprint)
                .project(project)
                .assignee(projectMember)
                .status(ItemStatus.TODO)
                .comments(Collections.emptyList())
                .build();

        SprintResponse sprintResponse = SprintResponse.builder()
                .sprintId(sprint.getSprintId())
                .projectId(sprint.getProject().getProjectId())
                .sprintName(sprint.getSprintName())
                .sprintDescription(sprint.getSprintDescription())
                .startDate(sprint.getStartDate())
                .endDate(sprint.getEndDate())
                .build();

        ProjectResponse projectResponse = ProjectResponse.builder()
                .sprints(List.of(sprintResponse))
                .name(project.getName())
                .build();
        UserResponse projectMemberResponse = UserResponse
                .builder()
                .username(projectMember.getUser().getUsername())
                .surname(projectMember.getUser().getSurname())
                .name(projectMember.getUser().getName())
                .userId(projectMember.getUser().getUserId())
                .build();

        BacklogItemDetails backlogItemDetails = BacklogItemDetails.builder()
                .sprint(sprintResponse)
                .comments(Collections.emptyList())
                .project(projectResponse)
                .assignee(projectMemberResponse)
                .build();

        return new BacklogItemDetailsTestData(backlogItem, backlogItemDetails);
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
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(2L))
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
