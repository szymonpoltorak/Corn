package dev.corn.cornbackend.repositories.backlog.comment;

import dev.corn.cornbackend.entities.backlog.comment.BacklogItemComment;
import dev.corn.cornbackend.entities.backlog.item.BacklogItem;
import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.project.member.ProjectMember;
import dev.corn.cornbackend.entities.sprint.Sprint;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.repositories.backlog.comment.data.BacklogItemCommentRepositoryTestData;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static dev.corn.cornbackend.repositories.SampleEntitiesBuilder.createSampleBacklogItem;
import static dev.corn.cornbackend.repositories.SampleEntitiesBuilder.createSampleBacklogItemComment;
import static dev.corn.cornbackend.repositories.SampleEntitiesBuilder.createSampleProject;
import static dev.corn.cornbackend.repositories.SampleEntitiesBuilder.createSampleProjectMember;
import static dev.corn.cornbackend.repositories.SampleEntitiesBuilder.createSampleSprint;
import static dev.corn.cornbackend.repositories.SampleEntitiesBuilder.createSampleUser;

public class BacklogItemCommentRepositoryTestDataBuilder {

    public static BacklogItemCommentRepositoryTestData backlogItemCommentRepositoryTestData(TestEntityManager testEntityManager) {
        User owner = createSampleUser(1L, "owner");
        User commentOwner = createSampleUser(2L, "commentOwner");
        User nonCommentOwner = createSampleUser(3L, "nonCommentOwner");
        User nonProjectMember = createSampleUser(4L, "nonProjectMember");
        Project project = createSampleProject(1L, "project");
        Sprint sprint = createSampleSprint(1L);
        ProjectMember commentOwnerMember = createSampleProjectMember(1L);
        ProjectMember nonCommentOwnerMember = createSampleProjectMember(2L);
        BacklogItem backlogItemWithComment = createSampleBacklogItem(1L);
        BacklogItemComment comment = createSampleBacklogItemComment(1L);

        testEntityManager.merge(owner);
        testEntityManager.merge(commentOwner);
        testEntityManager.merge(nonCommentOwner);
        testEntityManager.merge(nonProjectMember);

        project.setOwner(owner);

        testEntityManager.merge(project);

        sprint.setProject(project);

        testEntityManager.merge(sprint);

        commentOwnerMember.setUser(commentOwner);
        commentOwnerMember.setProject(project);
        nonCommentOwnerMember.setUser(nonCommentOwner);
        nonCommentOwnerMember.setProject(project);

        testEntityManager.merge(commentOwnerMember);
        testEntityManager.merge(nonCommentOwnerMember);

        backlogItemWithComment.setAssignee(commentOwnerMember);
        backlogItemWithComment.setProject(project);
        backlogItemWithComment.setSprint(sprint);

        testEntityManager.merge(backlogItemWithComment);

        comment.setUser(commentOwner);
        comment.setBacklogItem(backlogItemWithComment);

        testEntityManager.merge(comment);

        return BacklogItemCommentRepositoryTestData.builder()
                .owner(testEntityManager.find(User.class, owner.getUserId()))
                .commentOwner(testEntityManager.find(User.class, commentOwner.getUserId()))
                .nonCommentOwner(testEntityManager.find(User.class, nonCommentOwner.getUserId()))
                .nonProjectMember(testEntityManager.find(User.class, nonProjectMember.getUserId()))
                .comment(testEntityManager.find(BacklogItemComment.class, comment.getBacklogItemCommentId()))
                .build();
    }
}
