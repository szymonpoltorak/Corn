package dev.corn.cornbackend.repositories.sprint;

import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.sprint.Sprint;
import dev.corn.cornbackend.entities.sprint.interfaces.SprintRepository;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.repositories.sprint.data.SprintRepositoryTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static dev.corn.cornbackend.repositories.SampleEntitiesBuilder.createSampleProject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class SprintRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private SprintRepository sprintRepository;

    private static SprintRepositoryTestData TEST_DATA;

    @BeforeEach
    public void setUp() {
        TEST_DATA = SprintRepositoryTestDataBuilder.sprintRepositoryTestData(testEntityManager);
    }

    @Test
    void test_findAllByProjectIdShouldReturnSprintsWhenGivenUserIsOwnerOfProject() {
        //given
        User owner = TEST_DATA.projectOwner();
        Pageable pageable = PageRequest.of(0, 1);
        long projectId = TEST_DATA.projectId();

        //when
        Page<Sprint> sprints = sprintRepository.findAllByProjectId(projectId, owner, pageable);

        //then
        assertEquals(1L, sprints.getTotalElements());
        assertTrue(sprints.getContent().contains(TEST_DATA.sprint()));
    }

    @Test
    void test_findAllByProjectIdShouldReturnSpritnsWhenGivenUserIsMemberOfProject() {
        //given
        User member = TEST_DATA.projectMember();
        Pageable pageable = PageRequest.of(0, 1);
        long projectId = TEST_DATA.projectId();

        //when
        Page<Sprint> sprints = sprintRepository.findAllByProjectId(projectId, member, pageable);

        //then
        assertEquals(1L, sprints.getTotalElements());
        assertTrue(sprints.getContent().contains(TEST_DATA.sprint()));
    }

    @Test
    void test_findAllByProjectIdShouldReturnEmptyPageWhenGivenUserIsNotAMemberOfProject() {
        //given
        User nonMember = TEST_DATA.nonProjectMember();
        Pageable pageable = PageRequest.of(0, 1);
        long projectId = TEST_DATA.projectId();

        //when
        Page<Sprint> sprints = sprintRepository.findAllByProjectId(projectId, nonMember, pageable);

        //then
        assertEquals(0L, sprints.getTotalElements());
    }

    @Test
    void test_findByIdWithProjectMemberShouldReturnCorrectSprintWhenUserIsOwnerOfProject() {
        //given
        User owner = TEST_DATA.projectOwner();
        long sprintId = TEST_DATA.sprint().getSprintId();

        //when
        Optional<Sprint> sprint = sprintRepository.findByIdWithProjectMember(sprintId, owner);

        //then
        assertTrue(sprint.isPresent());
        assertEquals(TEST_DATA.sprint(), sprint.get());
    }

    @Test
    void test_findByIdWithProjectMemberShouldReturnCorrectSprintWhenUserIsMemberOfProject() {
        //given
        User member = TEST_DATA.projectMember();
        long sprintId = TEST_DATA.sprint().getSprintId();

        //when
        Optional<Sprint> sprint = sprintRepository.findByIdWithProjectMember(sprintId, member);

        //then
        assertTrue(sprint.isPresent());
        assertEquals(TEST_DATA.sprint(), sprint.get());
    }

    @Test
    void test_findByIdWithProjectMemberShouldReturnEmptyOptionalWhenUserIsNotOwnerOrMemberOfProject() {
        //given
        User nonMember = TEST_DATA.nonProjectMember();
        long sprintId = TEST_DATA.sprint().getSprintId();

        //when
        Optional<Sprint> sprint = sprintRepository.findByIdWithProjectMember(sprintId, nonMember);

        //then
        assertTrue(sprint.isEmpty());
    }

    @Test
    void test_findByIdWithProjectMemberShouldReturnEmptyOptionalWhenGivenIdIsIncorrect() {
        //given
        User owner = TEST_DATA.projectOwner();
        long sprintId = -1L;

        //when
        Optional<Sprint> sprint = sprintRepository.findByIdWithProjectMember(sprintId, owner);

        //then
        assertTrue(sprint.isEmpty());
    }

    @Test
    void test_findBySprintIdAndProjectShouldReturnCorrectSprint() {
        //given
        Project project = TEST_DATA.sprint().getProject();
        long sprintId = TEST_DATA.sprint().getSprintId();

        //when
        Optional<Sprint> sprint = sprintRepository.findBySprintIdAndProject(sprintId, project);

        //then
        assertTrue(sprint.isPresent());
        assertEquals(TEST_DATA.sprint(), sprint.get());
    }

    @Test
    void test_findBySprintIdAndProjectShouldReturnEmptyOptionalOnIncorrectId() {
        //given
        Project project = TEST_DATA.sprint().getProject();
        long sprintId = -1L;

        //when
        Optional<Sprint> sprint = sprintRepository.findBySprintIdAndProject(sprintId, project);

        //then
        assertTrue(sprint.isEmpty());
    }

    @Test
    void test_findBySprintIdAndProjectShouldReturnEmptyOptionalOnIncorrectProject() {
        //given
        Project project = createSampleProject(2L, "Incorrect project");
        long sprintId = TEST_DATA.sprint().getSprintId();

        //when
        Optional<Sprint> sprint = sprintRepository.findBySprintIdAndProject(sprintId, project);

        //then
        assertTrue(sprint.isEmpty());
    }

    @Test
    void test_findByIdWithProjectOwnerShouldReturnSprintOnOwner() {
        //given
        User owner = TEST_DATA.projectOwner();
        long sprintId = TEST_DATA.sprint().getSprintId();

        //when
        Optional<Sprint> sprint = sprintRepository.findByIdWithProjectOwner(sprintId, owner);

        //then
        assertTrue(sprint.isPresent());
        assertEquals(TEST_DATA.sprint(), sprint.get());
    }

    @Test
    void test_findByIdWithProjectOwnerShouldReturnEmptyOptionalOnProjectMember() {
        //given
        User member = TEST_DATA.projectMember();
        long sprintId = TEST_DATA.sprint().getSprintId();

        //when
        Optional<Sprint> sprint = sprintRepository.findByIdWithProjectOwner(sprintId, member);

        //then
        assertTrue(sprint.isEmpty());
    }

    @Test
    void test_findByIdWithProjectOwnerShouldReturnEmptyOptionalOnNonProjectMember() {
        //given
        User nonMember = TEST_DATA.nonProjectMember();
        long sprintId = TEST_DATA.sprint().getSprintId();

        //when
        Optional<Sprint> sprint = sprintRepository.findByIdWithProjectOwner(sprintId, nonMember);

        //then
        assertTrue(sprint.isEmpty());
    }

    @Test
    void test_findByIdWithProjectOwnerShouldReturnEmptyOptionalOnIncorrectId() {
        //given
        User owner = TEST_DATA.projectOwner();
        long sprintId = -1L;

        //when
        Optional<Sprint> sprint = sprintRepository.findByIdWithProjectOwner(sprintId, owner);

        //then
        assertTrue(sprint.isEmpty());
    }
}