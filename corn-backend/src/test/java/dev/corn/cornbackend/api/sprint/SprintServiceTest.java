package dev.corn.cornbackend.api.sprint;

import dev.corn.cornbackend.api.sprint.data.SprintRequest;
import dev.corn.cornbackend.api.sprint.data.SprintResponse;
import dev.corn.cornbackend.entities.backlog.item.interfaces.BacklogItemRepository;
import dev.corn.cornbackend.entities.project.interfaces.ProjectRepository;
import dev.corn.cornbackend.entities.sprint.Sprint;
import dev.corn.cornbackend.entities.sprint.interfaces.SprintMapper;
import dev.corn.cornbackend.entities.sprint.interfaces.SprintRepository;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.test.sprint.SprintTestDataBuilder;
import dev.corn.cornbackend.test.sprint.data.AddNewSprintData;
import dev.corn.cornbackend.utils.exceptions.project.ProjectDoesNotExistException;
import dev.corn.cornbackend.utils.exceptions.sprint.InvalidSprintDateException;
import dev.corn.cornbackend.utils.exceptions.sprint.SprintDoesNotExistException;
import dev.corn.cornbackend.utils.exceptions.sprint.SprintEndDateMustBeAfterStartDate;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = SprintRepository.class)
class SprintServiceTest {

    private static final String SHOULD_THROW_SPRINT_DOES_NOT_EXIST_EXCEPTION = "Should throw SprintDoesNotExistException";
    static final String SPRINT_RESPONSE_SHOULD_BE_EQUAL_TO_EXPECTED = "SprintResponse should be equal to expected";

    @InjectMocks
    private SprintServiceImpl sprintService;

    @Mock
    private SprintRepository sprintRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private BacklogItemRepository backlogItemRepository;

    @Mock
    private SprintMapper MAPPER;

    private static final AddNewSprintData ADD_SPRINT_DATA = SprintTestDataBuilder.addNewProjectData();

    @Test
    final void test_addNewSprint_shouldAddNewSprint() {
        // given
        SprintResponse expected = MAPPER.toSprintResponse(ADD_SPRINT_DATA.asSprint());
        User owner = ADD_SPRINT_DATA.project().getOwner();

        // when
        when(sprintRepository.save(ADD_SPRINT_DATA.asSprint()))
                .thenReturn(ADD_SPRINT_DATA.asSprint());
        when(projectRepository.findByProjectIdAndOwner(ADD_SPRINT_DATA.asSprint().getSprintId(), owner))
                .thenReturn(Optional.of(ADD_SPRINT_DATA.project()));
        when(MAPPER.toSprintResponse(ADD_SPRINT_DATA.asSprint()))
                .thenReturn(expected);

        SprintResponse actual = sprintService.addNewSprint(
                ADD_SPRINT_DATA.asSprintRequest(),
                owner
        );

        // then
        assertEquals(expected, actual, SPRINT_RESPONSE_SHOULD_BE_EQUAL_TO_EXPECTED);
        verify(sprintRepository).save(ADD_SPRINT_DATA.asSprint());
    }

    @Test
    final void test_addNewSprint_shouldThrowWhenProjectNotFound() {
        // given
        SprintResponse expected = MAPPER.toSprintResponse(ADD_SPRINT_DATA.asSprint());
        User owner = ADD_SPRINT_DATA.project().getOwner();

        // when
        when(sprintRepository.save(ADD_SPRINT_DATA.asSprint()))
                .thenReturn(ADD_SPRINT_DATA.asSprint());
        when(projectRepository.findByProjectIdAndOwner(ADD_SPRINT_DATA.asSprint().getSprintId(), owner))
                .thenReturn(Optional.empty());
        when(MAPPER.toSprintResponse(ADD_SPRINT_DATA.asSprint()))
                .thenReturn(expected);

        // then
        assertThrows(ProjectDoesNotExistException.class, () -> {
            sprintService.addNewSprint(
                    ADD_SPRINT_DATA.asSprintRequest(),
                    owner
            );
        }, "Should throw ProjectDoesNotExistException");
    }

    @Test
    final void test_addNewSprint_shouldThrowWhenEndIsBeforeStart() {
        // given
        SprintResponse expected = MAPPER.toSprintResponse(ADD_SPRINT_DATA.asSprint());

        // when
        when(sprintRepository.save(ADD_SPRINT_DATA.asSprint()))
                .thenReturn(ADD_SPRINT_DATA.asSprint());
        when(MAPPER.toSprintResponse(ADD_SPRINT_DATA.asSprint()))
                .thenReturn(expected);

        // then
        assertThrows(SprintEndDateMustBeAfterStartDate.class, () -> {
            SprintRequest sprintRequest = ADD_SPRINT_DATA.asSprintRequest();
            sprintRequest = SprintRequest.builder()
                    .name(sprintRequest.name())
                    .startDate(sprintRequest.endDate())
                    .endDate(sprintRequest.startDate())
                    .description(sprintRequest.description())
                    .build();
            sprintService.addNewSprint(
                    sprintRequest,
                    ADD_SPRINT_DATA.project().getOwner()
            );
        }, "Should throw SprintDoesNotExistException");
    }

    @Test
    final void test_getSprintById_shouldGetSprintById() {
        // given
        final long sprintId = 1L;
        User user = ADD_SPRINT_DATA.project().getOwner();
        SprintResponse expected = MAPPER.toSprintResponse(ADD_SPRINT_DATA.asSprint());

        // when
        when(sprintRepository.findByIdWithProjectMember(sprintId, user))
                .thenReturn(Optional.of(ADD_SPRINT_DATA.asSprint()));
        when(MAPPER.toSprintResponse(ADD_SPRINT_DATA.asSprint()))
                .thenReturn(expected);

        SprintResponse actual = sprintService.getSprintById(sprintId, user);

        // then
        assertEquals(expected, actual, SPRINT_RESPONSE_SHOULD_BE_EQUAL_TO_EXPECTED);
    }

    @Test
    final void test_getSprintsOnPage_shouldGetSprintsOnPage() {
        // given
        final int page = 0;
        User user = ADD_SPRINT_DATA.project().getOwner();
        Pageable pageable = PageRequest.of(page, SprintServiceImpl.SPRINTS_PER_PAGE);

        // when
        when(sprintRepository.findAllByProjectId(ADD_SPRINT_DATA.project().getProjectId(), user, pageable))
                .thenReturn(new PageImpl<>(List.of(ADD_SPRINT_DATA.asSprint())));
        SprintResponse response = new SprintResponse(0, 0, null, null, null, null);
        when(MAPPER.toSprintResponse(ADD_SPRINT_DATA.asSprint()))
                .thenReturn(response);

        List<SprintResponse> actual = sprintService.getSprintsOnPage(page, ADD_SPRINT_DATA.project().getProjectId(),
                ADD_SPRINT_DATA.project().getOwner());

        // then
        assertEquals(List.of(response), actual, "List of SprintResponse should not be empty");
    }

    @Test
    final void test_updateSprintsName_shouldThrowSprintDoesNotExistException() {
        // given
        final String newName = "newName";
        final long sprintId = 1L;

        // when
        when(sprintRepository.findById(sprintId))
                .thenReturn(Optional.empty());

        // then
        assertThrows(SprintDoesNotExistException.class,
                () -> sprintService.updateSprintsName(newName, sprintId, ADD_SPRINT_DATA.project().getOwner()),
                SHOULD_THROW_SPRINT_DOES_NOT_EXIST_EXCEPTION);
    }

    @Test
    final void test_updateSprintsName_shouldUpdateSprintsName() {
        // given
        final String newName = "newName";
        final long sprintId = 1L;
        SprintResponse expected = MAPPER.toSprintResponse(ADD_SPRINT_DATA.asSprint());
        User owner = ADD_SPRINT_DATA.project().getOwner();

        // when
        when(sprintRepository.findByIdWithProjectOwner(sprintId, owner))
                .thenReturn(Optional.of(ADD_SPRINT_DATA.asSprint()));
        when(sprintRepository.save(ADD_SPRINT_DATA.asSprint()))
                .thenReturn(ADD_SPRINT_DATA.asSprint());
        when(MAPPER.toSprintResponse(ADD_SPRINT_DATA.asSprint()))
                .thenReturn(expected);

        SprintResponse actual = sprintService.updateSprintsName(newName, sprintId, owner);

        // then
        assertEquals(expected, actual, SPRINT_RESPONSE_SHOULD_BE_EQUAL_TO_EXPECTED);
    }

    @Test
    final void test_updateSprintsDescription_shouldThrowSprintDoesNotExistException() {
        // given
        final String newDescription = "newDescription";
        final long sprintId = 1L;
        User owner = ADD_SPRINT_DATA.project().getOwner();

        // when
        when(sprintRepository.findByIdWithProjectOwner(sprintId, owner))
                .thenReturn(Optional.empty());

        // then
        assertThrows(SprintDoesNotExistException.class,
                () -> sprintService.updateSprintsDescription(newDescription, sprintId, owner),
                SHOULD_THROW_SPRINT_DOES_NOT_EXIST_EXCEPTION);
    }

    @Test
    final void test_updateSprintsDescription_shouldUpdateSprintsDescription() {
        // given
        final String newDescription = "newDescription";
        final long sprintId = 1L;
        SprintResponse expected = MAPPER.toSprintResponse(ADD_SPRINT_DATA.asSprint());
        User owner = ADD_SPRINT_DATA.project().getOwner();

        // when
        when(sprintRepository.findByIdWithProjectOwner(sprintId, owner))
                .thenReturn(Optional.of(ADD_SPRINT_DATA.asSprint()));
        when(sprintRepository.save(ADD_SPRINT_DATA.asSprint()))
                .thenReturn(ADD_SPRINT_DATA.asSprint());
        when(MAPPER.toSprintResponse(ADD_SPRINT_DATA.asSprint()))
                .thenReturn(expected);

        SprintResponse actual = sprintService.updateSprintsDescription(newDescription, sprintId, owner);

        // then
        assertEquals(expected, actual, SPRINT_RESPONSE_SHOULD_BE_EQUAL_TO_EXPECTED);
    }

    @Test
    final void test_updateSprintsStartDate_shouldThrowSprintDoesNotExistException() {
        // given
        final LocalDate newStartDate = LocalDate.now();
        final long sprintId = 1L;
        User owner = ADD_SPRINT_DATA.project().getOwner();

        // when
        when(sprintRepository.findByIdWithProjectOwner(sprintId, owner))
                .thenReturn(Optional.empty());

        // then
        assertThrows(SprintDoesNotExistException.class,
                () -> sprintService.updateSprintsStartDate(newStartDate, sprintId, owner),
                SHOULD_THROW_SPRINT_DOES_NOT_EXIST_EXCEPTION);
    }

    @Test
    final void test_updateSprintsStartDate_shouldThrowWhenEndIsBeforeStart() {
        // given
        Sprint sprint = ADD_SPRINT_DATA.asSprint();
        LocalDate newStartDate = sprint.getEndDate().plusDays(10);
        User owner = ADD_SPRINT_DATA.project().getOwner();

        // when
        when(sprintRepository.findByIdWithProjectOwner(sprint.getSprintId(), owner))
                .thenReturn(Optional.of(sprint));

        // then
        assertThrows(SprintEndDateMustBeAfterStartDate.class,
                () -> sprintService.updateSprintsStartDate(newStartDate, sprint.getSprintId(), owner),
                SHOULD_THROW_SPRINT_DOES_NOT_EXIST_EXCEPTION);
    }

    @Test
    final void test_updateSprintsStartDate_shouldUpdateSprintsStartDate() {
        // given
        final LocalDate newStartDate = LocalDate.now();
        final long sprintId = 1L;
        SprintResponse expected = MAPPER.toSprintResponse(ADD_SPRINT_DATA.asSprint());
        User owner = ADD_SPRINT_DATA.project().getOwner();
        // when
        when(sprintRepository.findByIdWithProjectOwner(sprintId, owner))
                .thenReturn(Optional.of(ADD_SPRINT_DATA.asSprint()));
        when(sprintRepository.save(ADD_SPRINT_DATA.asSprint()))
                .thenReturn(ADD_SPRINT_DATA.asSprint());
        when(MAPPER.toSprintResponse(ADD_SPRINT_DATA.asSprint()))
                .thenReturn(expected);

        SprintResponse actual = sprintService.updateSprintsStartDate(newStartDate, sprintId, owner);

        // then
        assertEquals(expected, actual, SPRINT_RESPONSE_SHOULD_BE_EQUAL_TO_EXPECTED);
    }

    @Test
    final void test_updateSprintsEndDate_shouldThrowSprintDoesNotExistException() {
        // given
        final LocalDate newEndDate = LocalDate.now().plusDays(7);
        final long sprintId = 1L;
        User owner = ADD_SPRINT_DATA.project().getOwner();

        // when
        when(sprintRepository.findByIdWithProjectOwner(sprintId, owner))
                .thenReturn(Optional.empty());

        // then
        assertThrows(SprintDoesNotExistException.class,
                () -> sprintService.updateSprintsEndDate(newEndDate, sprintId, owner),
                "Should throw SprintDoesNotExistException");
    }

    @Test
    final void test_updateSprintsEndDate_shouldThrowWhenEndIsBeforeStart() {
        // given
        Sprint sprint = ADD_SPRINT_DATA.asSprint();
        LocalDate newEndDate = sprint.getStartDate().minusDays(10);
        User owner = ADD_SPRINT_DATA.project().getOwner();

        // when
        when(sprintRepository.findByIdWithProjectOwner(sprint.getSprintId(), owner))
                .thenReturn(Optional.of(sprint));

        // then
        assertThrows(SprintEndDateMustBeAfterStartDate.class,
                () -> sprintService.updateSprintsEndDate(newEndDate, sprint.getSprintId(), owner),
                "Should throw SprintDoesNotExistException");
    }

    @Test
    final void test_updateSprintsEndDate_shouldUpdateSprintsEndDate() {
        // given
        final LocalDate newEndDate = LocalDate.now().plusDays(7);
        final long sprintId = 1L;
        SprintResponse expected = MAPPER.toSprintResponse(ADD_SPRINT_DATA.asSprint());
        User owner = ADD_SPRINT_DATA.project().getOwner();

        // when
        when(sprintRepository.findByIdWithProjectOwner(sprintId, owner))
                .thenReturn(Optional.of(ADD_SPRINT_DATA.asSprint()));
        when(sprintRepository.save(ADD_SPRINT_DATA.asSprint()))
                .thenReturn(ADD_SPRINT_DATA.asSprint());
        when(MAPPER.toSprintResponse(ADD_SPRINT_DATA.asSprint()))
                .thenReturn(expected);

        SprintResponse actual = sprintService.updateSprintsEndDate(newEndDate, sprintId, owner);

        // then
        assertEquals(expected, actual, SPRINT_RESPONSE_SHOULD_BE_EQUAL_TO_EXPECTED);
    }

    @Test
    final void test_deleteSprint_shouldThrowSprintDoesNotExistException() {
        // given
        final long sprintId = 1L;
        User owner = ADD_SPRINT_DATA.project().getOwner();

        // when
        when(sprintRepository.findByIdWithProjectOwner(sprintId, owner))
                .thenReturn(Optional.empty());

        // then
        assertThrows(SprintDoesNotExistException.class,
                () -> sprintService.deleteSprint(sprintId, owner),
                SHOULD_THROW_SPRINT_DOES_NOT_EXIST_EXCEPTION);
    }

    @Test
    final void test_deleteSprint_shouldDeleteSprint() {
        // given
        final long sprintId = 1L;
        SprintResponse expected = MAPPER.toSprintResponse(ADD_SPRINT_DATA.asSprint());
        User owner = ADD_SPRINT_DATA.project().getOwner();

        // when
        when(sprintRepository.findByIdWithProjectOwner(sprintId, owner))
                .thenReturn(Optional.of(ADD_SPRINT_DATA.asSprint()));
        when(MAPPER.toSprintResponse(ADD_SPRINT_DATA.asSprint()))
                .thenReturn(expected);

        SprintResponse actual = sprintService.deleteSprint(sprintId, owner);

        // then
        assertEquals(expected, actual, SPRINT_RESPONSE_SHOULD_BE_EQUAL_TO_EXPECTED);
    }

    @Test
    final void test_getCurrentAndFutureSprints_shouldReturnSprintResponseList() {
        // given
        long projectId = 1L;
        User user = ADD_SPRINT_DATA.project().getOwner();

        // when
        when(projectRepository.findByIdWithProjectMember(projectId, user))
                .thenReturn(Optional.of(ADD_SPRINT_DATA.project()));
        when(sprintRepository.findAllByProjectAndEndDateAfter(
                any(), any(), any()))
                .thenReturn(new PageImpl<>(List.of(ADD_SPRINT_DATA.asSprint())));
        when(MAPPER.toSprintResponse(ADD_SPRINT_DATA.asSprint()))
                .thenReturn(ADD_SPRINT_DATA.asSprintResponse());

        List<SprintResponse> expected = List.of(MAPPER.toSprintResponse(ADD_SPRINT_DATA.asSprint()));
        // then
        assertEquals(expected, sprintService.getCurrentAndFutureSprints(projectId, user));
    }

    @Test
    final void test_getCurrentAndFutureSprints_shouldThrowProjectDoesNotExistExceptionOnIncorrectProjectId() {
        // given
        long projectId = -1L;
        User user = ADD_SPRINT_DATA.project().getOwner();

        // when
        when(projectRepository.findByIdWithProjectMember(projectId, user))
                .thenReturn(Optional.empty());

        // then
        assertThrows(ProjectDoesNotExistException.class, () ->
                sprintService.getCurrentAndFutureSprints(projectId, user));
    }

    @Test
    final void test_addNewSprint_shouldThrowInvalidSprintDateException() {
        // given
        SprintRequest expected = ADD_SPRINT_DATA.asSprintRequest();
        User owner = ADD_SPRINT_DATA.project().getOwner();

        // when
        when(sprintRepository.existsBetweenStartDateAndEndDate(ADD_SPRINT_DATA.asSprint().getStartDate(),
                ADD_SPRINT_DATA.asSprint().getEndDate(), ADD_SPRINT_DATA.project().getProjectId()))
                .thenReturn(true);

        // then
        assertThrows(InvalidSprintDateException.class, () -> {
            sprintService.addNewSprint(expected, owner);
        });
    }

    @Test
    final void test_updateSprintsStartDate_shouldThrowInvalidSprintDateException() {
        // given
        LocalDate newStartDate = LocalDate.now();
        final long sprintId = 1L;
        User owner = ADD_SPRINT_DATA.project().getOwner();

        // when
        when(sprintRepository.existsSprintPeriodWithGivenDate(newStartDate, sprintId))
                .thenReturn(true);

        // then
        assertThrows(InvalidSprintDateException.class, () -> {
            sprintService.updateSprintsStartDate(newStartDate, sprintId, owner);
        });
    }

    @Test
    final void test_getSprintsAfterSprint_shouldReturnListOfSprints() {
        // given
        long sprintId = 1L;
        Pageable pageable = PageRequest.of(0, 5);
        List<Sprint> sprints = List.of(ADD_SPRINT_DATA.asSprint());
        List<SprintResponse> sprintResponses = List.of(ADD_SPRINT_DATA.asSprintResponse());
        Page<Sprint> pageOfSprints = new PageImpl<>(sprints, pageable, sprints.size());
        Page<SprintResponse> expected = new PageImpl<>(sprintResponses, pageable, sprintResponses.size());

        // when
        when(sprintRepository.findAllByProjectAndStartDateAfter(ADD_SPRINT_DATA.project(),
                ADD_SPRINT_DATA.asSprint().getStartDate(), pageable)).thenReturn(pageOfSprints);
        when(MAPPER.toSprintResponse(ADD_SPRINT_DATA.asSprint()))
                .thenReturn(ADD_SPRINT_DATA.asSprintResponse());
        when(sprintRepository.findByIdWithProjectMember(sprintId, ADD_SPRINT_DATA.project().getOwner()))
                .thenReturn(Optional.of(ADD_SPRINT_DATA.asSprint()));

        Page<SprintResponse> actual = sprintService.getSprintsAfterSprint(sprintId, pageable,
                ADD_SPRINT_DATA.project().getOwner());

        // then
        assertEquals(expected, actual, SPRINT_RESPONSE_SHOULD_BE_EQUAL_TO_EXPECTED);
    }

    @Test
    final void test_getSprintsBeforeSprint_shouldReturnListOfSprints() {
        // given
        long sprintId = 1L;
        Pageable pageable = PageRequest.of(0, 5);
        List<Sprint> sprints = List.of(ADD_SPRINT_DATA.asSprint());
        List<SprintResponse> sprintResponses = List.of(ADD_SPRINT_DATA.asSprintResponse());
        Page<Sprint> pageOfSprints = new PageImpl<>(sprints, pageable, sprints.size());
        Page<SprintResponse> expected = new PageImpl<>(sprintResponses, pageable, sprintResponses.size());

        // when
        when(sprintRepository.findAllByProjectAndEndDateBefore(ADD_SPRINT_DATA.project(),
                ADD_SPRINT_DATA.asSprint().getEndDate(), pageable)).thenReturn(pageOfSprints);
        when(MAPPER.toSprintResponse(ADD_SPRINT_DATA.asSprint()))
                .thenReturn(ADD_SPRINT_DATA.asSprintResponse());
        when(sprintRepository.findByIdWithProjectMember(sprintId, ADD_SPRINT_DATA.project().getOwner()))
                .thenReturn(Optional.of(ADD_SPRINT_DATA.asSprint()));

        Page<SprintResponse> actual = sprintService.getSprintsBeforeSprint(sprintId, pageable,
                ADD_SPRINT_DATA.project().getOwner());

        // then
        assertEquals(expected, actual, SPRINT_RESPONSE_SHOULD_BE_EQUAL_TO_EXPECTED);
    }

}