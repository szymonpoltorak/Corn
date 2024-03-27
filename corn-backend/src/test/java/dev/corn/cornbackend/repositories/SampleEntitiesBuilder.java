package dev.corn.cornbackend.repositories;

import dev.corn.cornbackend.entities.backlog.comment.BacklogItemComment;
import dev.corn.cornbackend.entities.backlog.item.BacklogItem;
import dev.corn.cornbackend.entities.backlog.item.enums.ItemStatus;
import dev.corn.cornbackend.entities.backlog.item.enums.ItemType;
import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.project.member.ProjectMember;
import dev.corn.cornbackend.entities.sprint.Sprint;
import dev.corn.cornbackend.entities.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

public class SampleEntitiesBuilder {

    public static BacklogItem createSampleBacklogItem(long id) {
        return BacklogItem.builder()
                .backlogItemId(id)
                .comments(Collections.emptyList())
                .sprint(null)
                .assignee(null)
                .description("Description")
                .project(null)
                .itemType(ItemType.TASK)
                .title("Title")
                .status(ItemStatus.TODO)
                .build();
    }

    public static User createSampleUser(long id, String username) {
        return User.builder()
                .name("Name")
                .userId(id)
                .surname("Surname")
                .name("Name")
                .username(username)
                .authorities(Collections.emptyList())
                .build();
    }

    public static Project createSampleProject(long id, String name) {
        return Project.builder()
                .projectId(id)
                .owner(null)
                .name(name)
                .sprints(Collections.emptyList())
                .build();
    }

    public static ProjectMember createSampleProjectMember(long id) {
        return ProjectMember.builder()
                .projectMemberId(id)
                .project(null)
                .user(null)
                .backlogItems(Collections.emptyList())
                .build();
    }

    public static Sprint createSampleSprint(long id) {
        return Sprint.builder()
                .sprintId(id)
                .project(null)
                .sprintDescription("Description")
                .sprintName("Name")
                .sprintStartDate(LocalDate.now())
                .sprintEndDate(LocalDate.now().plusDays(7))
                .build();
    }

    public static BacklogItemComment createSampleBacklogItemComment(long id) {
        return BacklogItemComment.builder()
                .backlogItemCommentId(id)
                .comment("Comment")
                .backlogItem(null)
                .user(null)
                .lastEditTime(LocalDateTime.now())
                .commentDate(LocalDateTime.now())
                .build();
    }
}
