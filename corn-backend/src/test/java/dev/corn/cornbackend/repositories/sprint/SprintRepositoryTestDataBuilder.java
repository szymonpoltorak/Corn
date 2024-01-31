package dev.corn.cornbackend.repositories.sprint;

import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.project.member.ProjectMember;
import dev.corn.cornbackend.entities.sprint.Sprint;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.repositories.sprint.data.SprintRepositoryTestData;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static dev.corn.cornbackend.repositories.SampleEntitiesBuilder.createSampleProject;
import static dev.corn.cornbackend.repositories.SampleEntitiesBuilder.createSampleProjectMember;
import static dev.corn.cornbackend.repositories.SampleEntitiesBuilder.createSampleSprint;
import static dev.corn.cornbackend.repositories.SampleEntitiesBuilder.createSampleUser;

public class SprintRepositoryTestDataBuilder {

    public static SprintRepositoryTestData sprintRepositoryTestData(TestEntityManager testEntityManager) {
        User projectOwner = createSampleUser(1L, "projectOwner");
        User projectMember = createSampleUser(2L, "projectMember");
        User nonProjectMember = createSampleUser(3L, "nonProjectMember");
        ProjectMember projectMemberMember = createSampleProjectMember(1L);
        Project project = createSampleProject(1L, "Project");
        Sprint sprint = createSampleSprint(1L);

        testEntityManager.merge(projectOwner);
        testEntityManager.merge(projectMember);
        testEntityManager.merge(nonProjectMember);

        project.setOwner(projectOwner);

        testEntityManager.merge(project);

        sprint.setProject(project);

        testEntityManager.merge(sprint);

        projectMemberMember.setUser(projectMember);
        projectMemberMember.setProject(project);

        testEntityManager.merge(projectMemberMember);

        return SprintRepositoryTestData.builder()
                .projectId(project.getProjectId())
                .projectMember(projectMember)
                .projectOwner(projectOwner)
                .nonProjectMember(nonProjectMember)
                .sprint(sprint)
                .build();
    }
}
