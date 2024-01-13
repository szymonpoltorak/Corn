package dev.corn.cornbackend.repositories.backlog.item;

import dev.corn.cornbackend.entities.backlog.item.BacklogItem;
import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.project.member.ProjectMember;
import dev.corn.cornbackend.entities.sprint.Sprint;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.repositories.backlog.item.data.BacklogItemRepositoryTestData;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static dev.corn.cornbackend.repositories.SampleEntitiesBuilder.createSampleBacklogItem;
import static dev.corn.cornbackend.repositories.SampleEntitiesBuilder.createSampleProject;
import static dev.corn.cornbackend.repositories.SampleEntitiesBuilder.createSampleProjectMember;
import static dev.corn.cornbackend.repositories.SampleEntitiesBuilder.createSampleSprint;
import static dev.corn.cornbackend.repositories.SampleEntitiesBuilder.createSampleUser;

public class BacklogItemRepositoryTestDataBuilder {

    public static BacklogItemRepositoryTestData backlogItemRepositoryTestData(TestEntityManager testEntityManager) {
        User owner = createSampleUser(1L, "owner");
        User projectMember = createSampleUser(2L, "projectMember");
        User nonProjectMember = createSampleUser(3L, "nonProjectMember");
        Project project = createSampleProject(1L, "project");
        ProjectMember projectMemberMember = createSampleProjectMember(1L);
        BacklogItem backlogItem = createSampleBacklogItem(1L);
        Sprint sprint = createSampleSprint(1L);

        testEntityManager.merge(owner);
        testEntityManager.merge(projectMember);
        testEntityManager.merge(nonProjectMember);

        project.setOwner(owner);

        testEntityManager.merge(project);

        sprint.setProject(project);

        testEntityManager.merge(sprint);

        projectMemberMember.setUser(projectMember);
        projectMemberMember.setProject(project);

        testEntityManager.merge(projectMemberMember);

        backlogItem.setAssignee(projectMemberMember);
        backlogItem.setProject(project);
        backlogItem.setSprint(sprint);

        testEntityManager.merge(backlogItem);

        return BacklogItemRepositoryTestData.builder()
                .backlogItem(testEntityManager.find(BacklogItem.class, backlogItem.getBacklogItemId()))
                .owner(testEntityManager.find(User.class, owner.getUserId()))
                .projectMember(testEntityManager.find(User.class, projectMember.getUserId()))
                .nonProjectMember(testEntityManager.find(User.class, nonProjectMember.getUserId()))
                .project(testEntityManager.find(Project.class, project.getProjectId()))
                .sprint(testEntityManager.find(Sprint.class, sprint.getSprintId()))
                .build();
    }
}
