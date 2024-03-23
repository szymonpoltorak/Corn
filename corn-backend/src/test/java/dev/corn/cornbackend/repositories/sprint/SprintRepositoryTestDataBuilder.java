package dev.corn.cornbackend.repositories.sprint;

import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.project.member.ProjectMember;
import dev.corn.cornbackend.entities.sprint.Sprint;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.repositories.sprint.data.SprintRepositoryTestData;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;

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
        Sprint currentSprint = createSampleSprint(1L);
        Sprint finishedSprint = createSampleSprint(2L);
        Sprint futureSprint = createSampleSprint(3L);

        currentSprint.setSprintStartDate(LocalDate.now().plusDays(3L));
        currentSprint.setSprintEndDate(LocalDate.now().plusDays(4L));

        finishedSprint.setSprintStartDate(LocalDate.now().plusDays(1L));
        finishedSprint.setSprintEndDate(LocalDate.now().plusDays(2L));

        futureSprint.setSprintStartDate(LocalDate.now().plusDays(5L));
        futureSprint.setSprintEndDate(LocalDate.now().plusDays(6L));

        testEntityManager.merge(projectOwner);
        testEntityManager.merge(projectMember);
        testEntityManager.merge(nonProjectMember);

        project.setOwner(projectOwner);

        testEntityManager.merge(project);

        currentSprint.setProject(project);
        finishedSprint.setProject(project);
        futureSprint.setProject(project);

        testEntityManager.merge(currentSprint);
        testEntityManager.merge(finishedSprint);
        testEntityManager.merge(futureSprint);

        projectMemberMember.setUser(projectMember);
        projectMemberMember.setProject(project);

        testEntityManager.merge(projectMemberMember);

        return SprintRepositoryTestData.builder()
                .projectId(project.getProjectId())
                .projectMember(projectMember)
                .projectOwner(projectOwner)
                .nonProjectMember(nonProjectMember)
                .currentSprint(currentSprint)
                .futureSprint(futureSprint)
                .finishedSprint(finishedSprint)
                .project(project)
                .build();
    }
}
