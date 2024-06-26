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

import java.time.LocalDate;
import java.util.List;
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

    private static SprintRepositoryTestData TEST_DATA = null;

    private static final String OPTIONAL_PRESENT = "Sprint optional should be present";
    private static final String OPTIONAL_EMPTY = "Sprint optional should be empty";
    private static final String PAGE_CORRECT_TOTAL_ELEMENTS = "Sprint page should contain correct number of total elements";
    private static final String SPRINT_EQUAL = "Sprints should be equal";

    @BeforeEach
    public final void setUp() {
        TEST_DATA = SprintRepositoryTestDataBuilder.sprintRepositoryTestData(testEntityManager);
    }

    @Test
    final void test_findAllByProjectIdShouldReturnSprintsWhenGivenUserIsOwnerOfProject() {
        //given
        User owner = TEST_DATA.projectOwner();
        Pageable pageable = PageRequest.of(0, 3);
        long projectId = TEST_DATA.projectId();

        //when
        Page<Sprint> sprints = sprintRepository.findAllByProjectId(projectId, owner, pageable);

        //then
        assertEquals(3, sprints.getNumberOfElements(), PAGE_CORRECT_TOTAL_ELEMENTS);
        assertTrue(sprints.getContent().contains(TEST_DATA.currentSprint()), SPRINT_EQUAL);
        assertTrue(sprints.getContent().contains(TEST_DATA.futureSprint()), SPRINT_EQUAL);
        assertTrue(sprints.getContent().contains(TEST_DATA.finishedSprint()), SPRINT_EQUAL);
    }

    @Test
    final void test_findAllByProjectIdShouldReturnSprintsWhenGivenUserIsMemberOfProject() {
        //given
        User member = TEST_DATA.projectMember();
        Pageable pageable = PageRequest.of(0, 3);
        long projectId = TEST_DATA.projectId();

        //when
        Page<Sprint> sprints = sprintRepository.findAllByProjectId(projectId, member, pageable);

        //then
        assertEquals(3, sprints.getNumberOfElements(), PAGE_CORRECT_TOTAL_ELEMENTS);
        assertTrue(sprints.getContent().contains(TEST_DATA.currentSprint()), SPRINT_EQUAL);
        assertTrue(sprints.getContent().contains(TEST_DATA.futureSprint()), SPRINT_EQUAL);
        assertTrue(sprints.getContent().contains(TEST_DATA.finishedSprint()), SPRINT_EQUAL);
    }

    @Test
    final void test_findAllByProjectIdShouldReturnEmptyPageWhenGivenUserIsNotAMemberOfProject() {
        //given
        User nonMember = TEST_DATA.nonProjectMember();
        Pageable pageable = PageRequest.of(0, 1);
        long projectId = TEST_DATA.projectId();

        //when
        Page<Sprint> sprints = sprintRepository.findAllByProjectId(projectId, nonMember, pageable);

        //then
        assertEquals(0L, sprints.getTotalElements(), PAGE_CORRECT_TOTAL_ELEMENTS);
    }

    @Test
    final void test_findByIdWithProjectMemberShouldReturnCorrectSprintWhenUserIsOwnerOfProject() {
        //given
        User owner = TEST_DATA.projectOwner();
        long sprintId = TEST_DATA.currentSprint().getSprintId();

        //when
        Optional<Sprint> sprint = sprintRepository.findByIdWithProjectMember(sprintId, owner);

        //then
        assertTrue(sprint.isPresent(), OPTIONAL_PRESENT);
        assertEquals(TEST_DATA.currentSprint(), sprint.get(), SPRINT_EQUAL);
    }

    @Test
    final void test_findByIdWithProjectMemberShouldReturnCorrectSprintWhenUserIsMemberOfProject() {
        //given
        User member = TEST_DATA.projectMember();
        long sprintId = TEST_DATA.currentSprint().getSprintId();

        //when
        Optional<Sprint> sprint = sprintRepository.findByIdWithProjectMember(sprintId, member);

        //then
        assertTrue(sprint.isPresent(), OPTIONAL_PRESENT);
        assertEquals(TEST_DATA.currentSprint(), sprint.get(), SPRINT_EQUAL);
    }

    @Test
    final void test_findByIdWithProjectMemberShouldReturnEmptyOptionalWhenUserIsNotOwnerOrMemberOfProject() {
        //given
        User nonMember = TEST_DATA.nonProjectMember();
        long sprintId = TEST_DATA.currentSprint().getSprintId();

        //when
        Optional<Sprint> sprint = sprintRepository.findByIdWithProjectMember(sprintId, nonMember);

        //then
        assertTrue(sprint.isEmpty(), OPTIONAL_EMPTY);
    }

    @Test
    final void test_findByIdWithProjectMemberShouldReturnEmptyOptionalWhenGivenIdIsIncorrect() {
        //given
        User owner = TEST_DATA.projectOwner();
        long sprintId = -1L;

        //when
        Optional<Sprint> sprint = sprintRepository.findByIdWithProjectMember(sprintId, owner);

        //then
        assertTrue(sprint.isEmpty(), OPTIONAL_EMPTY);
    }

    @Test
    final void test_findBySprintIdAndProjectShouldReturnCorrectSprint() {
        //given
        Project project = TEST_DATA.currentSprint().getProject();
        long sprintId = TEST_DATA.currentSprint().getSprintId();

        //when
        Optional<Sprint> sprint = sprintRepository.findBySprintIdAndProject(sprintId, project);

        //then
        assertTrue(sprint.isPresent(), OPTIONAL_PRESENT);
        assertEquals(TEST_DATA.currentSprint(), sprint.get(), SPRINT_EQUAL);
    }

    @Test
    final void test_findBySprintIdAndProjectShouldReturnEmptyOptionalOnIncorrectId() {
        //given
        Project project = TEST_DATA.currentSprint().getProject();
        long sprintId = -1L;

        //when
        Optional<Sprint> sprint = sprintRepository.findBySprintIdAndProject(sprintId, project);

        //then
        assertTrue(sprint.isEmpty(), OPTIONAL_EMPTY);
    }

    @Test
    final void test_findBySprintIdAndProjectShouldReturnEmptyOptionalOnIncorrectProject() {
        //given
        Project project = createSampleProject(2L, "Incorrect project");
        long sprintId = TEST_DATA.currentSprint().getSprintId();

        //when
        Optional<Sprint> sprint = sprintRepository.findBySprintIdAndProject(sprintId, project);

        //then
        assertTrue(sprint.isEmpty(), OPTIONAL_EMPTY);
    }

    @Test
    final void test_findByIdWithProjectOwnerShouldReturnSprintOnOwner() {
        //given
        User owner = TEST_DATA.projectOwner();
        long sprintId = TEST_DATA.currentSprint().getSprintId();

        //when
        Optional<Sprint> sprint = sprintRepository.findByIdWithProjectOwner(sprintId, owner);

        //then
        assertTrue(sprint.isPresent(), OPTIONAL_PRESENT);
        assertEquals(TEST_DATA.currentSprint(), sprint.get(), SPRINT_EQUAL);
    }

    @Test
    final void test_findByIdWithProjectOwnerShouldReturnEmptyOptionalOnProjectMember() {
        //given
        User member = TEST_DATA.projectMember();
        long sprintId = TEST_DATA.currentSprint().getSprintId();

        //when
        Optional<Sprint> sprint = sprintRepository.findByIdWithProjectOwner(sprintId, member);

        //then
        assertTrue(sprint.isEmpty(), OPTIONAL_EMPTY);
    }

    @Test
    final void test_findByIdWithProjectOwnerShouldReturnEmptyOptionalOnNonProjectMember() {
        //given
        User nonMember = TEST_DATA.nonProjectMember();
        long sprintId = TEST_DATA.currentSprint().getSprintId();

        //when
        Optional<Sprint> sprint = sprintRepository.findByIdWithProjectOwner(sprintId, nonMember);

        //then
        assertTrue(sprint.isEmpty(), OPTIONAL_EMPTY);
    }

    @Test
    final void test_findByIdWithProjectOwnerShouldReturnEmptyOptionalOnIncorrectId() {
        //given
        User owner = TEST_DATA.projectOwner();
        long sprintId = -1L;

        //when
        Optional<Sprint> sprint = sprintRepository.findByIdWithProjectOwner(sprintId, owner);

        //then
        assertTrue(sprint.isEmpty(), OPTIONAL_EMPTY);
    }

    @Test
    final void test_findAllByProjectAndEndDateAfterShouldReturnCorrectSprints() {
        //given
        Project project = TEST_DATA.project();
        LocalDate date = TEST_DATA.currentSprint().getStartDate();
        Pageable pageable = PageRequest.of(0, 3);

        //when
        Page<Sprint> sprints = sprintRepository.findAllByProjectAndEndDateAfter(project, date, pageable);

        //then
        assertEquals(2, sprints.getNumberOfElements());
        assertTrue(sprints.getContent().containsAll(List.of(TEST_DATA.currentSprint(), TEST_DATA.futureSprint())));
    }

    @Test
    final void test_findAllByProjectAndEndDateAfterShouldReturnEmptyPageWhenNoneOfSprintsEndsAfterDate() {
        //given
        Project project = TEST_DATA.project();
        LocalDate date = TEST_DATA.futureSprint().getEndDate().plusDays(1L);
        Pageable pageable = PageRequest.of(0, 3);

        //when
        Page<Sprint> sprints = sprintRepository.findAllByProjectAndEndDateAfter(project, date, pageable);

        //then
        assertEquals(0, sprints.getNumberOfElements());
    }
}
