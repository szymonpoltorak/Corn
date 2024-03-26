package dev.corn.cornbackend.repositories.project;

import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.project.member.ProjectMember;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.repositories.project.data.ProjectRepositoryTestData;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static dev.corn.cornbackend.repositories.SampleEntitiesBuilder.createSampleProject;
import static dev.corn.cornbackend.repositories.SampleEntitiesBuilder.createSampleProjectMember;
import static dev.corn.cornbackend.repositories.SampleEntitiesBuilder.createSampleUser;

public class ProjectRepositoryTestDataBuilder {

    public static ProjectRepositoryTestData projectRepositoryTestData(TestEntityManager testEntityManager) {
        User project1And2Owner = createSampleUser(1L, "project1And2Owner");
        User project1Member = createSampleUser(3L, "project1Member");
        User nonProjectMember = createSampleUser(4L, "nonProjectMember");
        ProjectMember project1MemberMember = createSampleProjectMember(1L);
        Project project1 = createSampleProject(1L, "project1");
        Project project2 = createSampleProject(2L, "project2");

        testEntityManager.merge(project1And2Owner);
        testEntityManager.merge(project1Member);
        testEntityManager.merge(nonProjectMember);

        project1.setOwner(project1And2Owner);
        project2.setOwner(project1And2Owner);

        testEntityManager.merge(project1);
        testEntityManager.merge(project2);

        project1MemberMember.setUser(project1Member);
        project1MemberMember.setProject(project1);

        testEntityManager.merge(project1MemberMember);

        return ProjectRepositoryTestData.builder()
                .project1(project1)
                .project2(project2)
                .project1And2Owner(project1And2Owner)
                .project1Member(project1Member)
                .nonProjectMember(nonProjectMember)
                .build();
    }
}
