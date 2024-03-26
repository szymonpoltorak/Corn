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
        BacklogItem backlogItemWithoutSprint = createSampleBacklogItem(2L);
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

        backlogItemWithoutSprint.setAssignee(projectMemberMember);
        backlogItemWithoutSprint.setProject(project);

        testEntityManager.merge(backlogItem);
        testEntityManager.merge(backlogItemWithoutSprint);

        return BacklogItemRepositoryTestData.builder()
                .backlogItem(backlogItem)
                .backlogItemWithoutSprint(backlogItemWithoutSprint)
                .owner(owner)
                .projectMember(projectMember)
                .nonProjectMember(nonProjectMember)
                .project(project)
                .sprint(sprint)
                .build();
    }
}
