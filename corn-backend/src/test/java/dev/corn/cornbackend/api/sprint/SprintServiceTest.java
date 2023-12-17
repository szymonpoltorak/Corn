package dev.corn.cornbackend.api.sprint;

import dev.corn.cornbackend.api.sprint.data.SprintRequest;
import dev.corn.cornbackend.api.sprint.data.SprintResponse;
import dev.corn.cornbackend.entities.sprint.Sprint;
import dev.corn.cornbackend.entities.sprint.interfaces.SprintMapper;
import dev.corn.cornbackend.entities.sprint.interfaces.SprintRepository;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.test.sprint.SprintTestDataBuilder;
import dev.corn.cornbackend.test.sprint.data.AddNewSprintData;
import dev.corn.cornbackend.utils.exceptions.sprint.SprintDoesNotExistException;
import dev.corn.cornbackend.utils.exceptions.sprint.SprintEndDateMustBeAfterStartDate;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    private SprintMapper MAPPER;

    private static final AddNewSprintData ADD_SPRINT_DATA = SprintTestDataBuilder.addNewProjectData();

    @Test
    final void test_addNewSprint_shouldAddNewSprint() {
        // given
        SprintResponse expected = MAPPER.toSprintResponse(ADD_SPRINT_DATA.asSprint());

        // when
        when(sprintRepository.save(ADD_SPRINT_DATA.asSprint()))
                .thenReturn(ADD_SPRINT_DATA.asSprint());
        when(MAPPER.toSprintResponse(ADD_SPRINT_DATA.asSprint()))
                .thenReturn(expected);

        SprintResponse actual = sprintService.addNewSprint(
                ADD_SPRINT_DATA.asSprintRequest(),
                ADD_SPRINT_DATA.project().getOwner()
        );

        // then
        assertEquals(expected, actual, SPRINT_RESPONSE_SHOULD_BE_EQUAL_TO_EXPECTED);
        verify(sprintRepository).save(ADD_SPRINT_DATA.asSprint());
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
        when(sprintRepository.findById(sprintId))
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
        when(sprintRepository.findAllByProjectId(ADD_SPRINT_DATA.project().getProjectId(), pageable))
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

        // when
        when(sprintRepository.findById(sprintId))
                .thenReturn(Optional.of(ADD_SPRINT_DATA.asSprint()));
        when(sprintRepository.save(ADD_SPRINT_DATA.asSprint()))
                .thenReturn(ADD_SPRINT_DATA.asSprint());
        when(MAPPER.toSprintResponse(ADD_SPRINT_DATA.asSprint()))
                .thenReturn(expected);

        SprintResponse actual = sprintService.updateSprintsName(newName, sprintId, ADD_SPRINT_DATA.project().getOwner());

        // then
        assertEquals(expected, actual, SPRINT_RESPONSE_SHOULD_BE_EQUAL_TO_EXPECTED);
    }

    @Test
    final void test_updateSprintsDescription_shouldThrowSprintDoesNotExistException() {
        // given
        final String newDescription = "newDescription";
        final long sprintId = 1L;

        // when
        when(sprintRepository.findById(sprintId))
                .thenReturn(Optional.empty());

        // then
        assertThrows(SprintDoesNotExistException.class,
                () -> sprintService.updateSprintsDescription(newDescription, sprintId, ADD_SPRINT_DATA.project().getOwner()),
                SHOULD_THROW_SPRINT_DOES_NOT_EXIST_EXCEPTION);
    }

    @Test
    final void test_updateSprintsDescription_shouldUpdateSprintsDescription() {
        // given
        final String newDescription = "newDescription";
        final long sprintId = 1L;
        SprintResponse expected = MAPPER.toSprintResponse(ADD_SPRINT_DATA.asSprint());

        // when
        when(sprintRepository.findById(sprintId))
                .thenReturn(Optional.of(ADD_SPRINT_DATA.asSprint()));
        when(sprintRepository.save(ADD_SPRINT_DATA.asSprint()))
                .thenReturn(ADD_SPRINT_DATA.asSprint());
        when(MAPPER.toSprintResponse(ADD_SPRINT_DATA.asSprint()))
                .thenReturn(expected);

        SprintResponse actual = sprintService.updateSprintsDescription(newDescription, sprintId, ADD_SPRINT_DATA.project().getOwner());

        // then
        assertEquals(expected, actual, SPRINT_RESPONSE_SHOULD_BE_EQUAL_TO_EXPECTED);
    }

    @Test
    final void test_updateSprintsStartDate_shouldThrowSprintDoesNotExistException() {
        // given
        final LocalDate newStartDate = LocalDate.now();
        final long sprintId = 1L;

        // when
        when(sprintRepository.findById(sprintId))
                .thenReturn(Optional.empty());

        // then
        assertThrows(SprintDoesNotExistException.class,
                () -> sprintService.updateSprintsStartDate(newStartDate, sprintId, ADD_SPRINT_DATA.project().getOwner()),
                SHOULD_THROW_SPRINT_DOES_NOT_EXIST_EXCEPTION);
    }

    @Test
    final void test_updateSprintsStartDate_shouldThrowWhenEndIsBeforeStart() {
        // given
        Sprint sprint = ADD_SPRINT_DATA.asSprint();
        LocalDate newStartDate = sprint.getSprintEndDate().plusDays(10);

        // when
        when(sprintRepository.findById(sprint.getSprintId()))
                .thenReturn(Optional.of(sprint));

        // then
        assertThrows(SprintEndDateMustBeAfterStartDate.class,
                () -> sprintService.updateSprintsStartDate(newStartDate, sprint.getSprintId(), ADD_SPRINT_DATA.project().getOwner()),
                SHOULD_THROW_SPRINT_DOES_NOT_EXIST_EXCEPTION);
    }

    @Test
    final void test_updateSprintsStartDate_shouldUpdateSprintsStartDate() {
        // given
        final LocalDate newStartDate = LocalDate.now();
        final long sprintId = 1L;
        SprintResponse expected = MAPPER.toSprintResponse(ADD_SPRINT_DATA.asSprint());

        // when
        when(sprintRepository.findById(sprintId))
                .thenReturn(Optional.of(ADD_SPRINT_DATA.asSprint()));
        when(sprintRepository.save(ADD_SPRINT_DATA.asSprint()))
                .thenReturn(ADD_SPRINT_DATA.asSprint());
        when(MAPPER.toSprintResponse(ADD_SPRINT_DATA.asSprint()))
                .thenReturn(expected);

        SprintResponse actual = sprintService.updateSprintsStartDate(newStartDate, sprintId, ADD_SPRINT_DATA.project().getOwner());

        // then
        assertEquals(expected, actual, SPRINT_RESPONSE_SHOULD_BE_EQUAL_TO_EXPECTED);
    }

    @Test
    final void test_updateSprintsEndDate_shouldThrowSprintDoesNotExistException() {
        // given
        final LocalDate newEndDate = LocalDate.now().plusDays(7);
        final long sprintId = 1L;

        // when
        when(sprintRepository.findById(sprintId))
                .thenReturn(Optional.empty());

        // then
        assertThrows(SprintDoesNotExistException.class,
                () -> sprintService.updateSprintsEndDate(newEndDate, sprintId, ADD_SPRINT_DATA.project().getOwner()),
                "Should throw SprintDoesNotExistException");
    }

    @Test
    final void test_updateSprintsEndDate_shouldThrowWhenEndIsBeforeStart() {
        // given
        Sprint sprint = ADD_SPRINT_DATA.asSprint();
        LocalDate newEndDate = sprint.getSprintStartDate().minusDays(10);

        // when
        when(sprintRepository.findById(sprint.getSprintId()))
                .thenReturn(Optional.of(sprint));

        // then
        assertThrows(SprintEndDateMustBeAfterStartDate.class,
                () -> sprintService.updateSprintsEndDate(newEndDate, sprint.getSprintId(), ADD_SPRINT_DATA.project().getOwner()),
                "Should throw SprintDoesNotExistException");
    }

    @Test
    final void test_updateSprintsEndDate_shouldUpdateSprintsEndDate() {
        // given
        final LocalDate newEndDate = LocalDate.now().plusDays(7);
        final long sprintId = 1L;
        SprintResponse expected = MAPPER.toSprintResponse(ADD_SPRINT_DATA.asSprint());

        // when
        when(sprintRepository.findById(sprintId))
                .thenReturn(Optional.of(ADD_SPRINT_DATA.asSprint()));
        when(sprintRepository.save(ADD_SPRINT_DATA.asSprint()))
                .thenReturn(ADD_SPRINT_DATA.asSprint());
        when(MAPPER.toSprintResponse(ADD_SPRINT_DATA.asSprint()))
                .thenReturn(expected);

        SprintResponse actual = sprintService.updateSprintsEndDate(newEndDate, sprintId, ADD_SPRINT_DATA.project().getOwner());

        // then
        assertEquals(expected, actual, SPRINT_RESPONSE_SHOULD_BE_EQUAL_TO_EXPECTED);
    }

    @Test
    final void test_deleteSprint_shouldThrowSprintDoesNotExistException() {
        // given
        final long sprintId = 1L;

        // when
        when(sprintRepository.findById(sprintId))
                .thenReturn(Optional.empty());

        // then
        assertThrows(SprintDoesNotExistException.class,
                () -> sprintService.deleteSprint(sprintId, ADD_SPRINT_DATA.project().getOwner()),
                SHOULD_THROW_SPRINT_DOES_NOT_EXIST_EXCEPTION);
    }

    @Test
    final void test_deleteSprint_shouldDeleteSprint() {
        // given
        final long sprintId = 1L;
        SprintResponse expected = MAPPER.toSprintResponse(ADD_SPRINT_DATA.asSprint());

        // when
        when(sprintRepository.findById(sprintId))
                .thenReturn(Optional.of(ADD_SPRINT_DATA.asSprint()));
        when(MAPPER.toSprintResponse(ADD_SPRINT_DATA.asSprint()))
                .thenReturn(expected);

        SprintResponse actual = sprintService.deleteSprint(sprintId, ADD_SPRINT_DATA.project().getOwner());

        // then
        assertEquals(expected, actual, SPRINT_RESPONSE_SHOULD_BE_EQUAL_TO_EXPECTED);
    }
}
