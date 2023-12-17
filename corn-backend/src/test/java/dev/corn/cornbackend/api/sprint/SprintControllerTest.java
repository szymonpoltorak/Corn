package dev.corn.cornbackend.api.sprint;


import dev.corn.cornbackend.api.sprint.data.SprintRequest;
import dev.corn.cornbackend.api.sprint.data.SprintResponse;
import dev.corn.cornbackend.api.sprint.interfaces.SprintService;
import dev.corn.cornbackend.entities.sprint.interfaces.SprintMapper;
import dev.corn.cornbackend.test.sprint.SprintTestDataBuilder;
import dev.corn.cornbackend.test.sprint.data.AddNewSprintData;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static dev.corn.cornbackend.api.sprint.SprintServiceTest.SPRINT_RESPONSE_SHOULD_BE_EQUAL_TO_EXPECTED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = SprintService.class)
class SprintControllerTest {

    @InjectMocks
    private SprintControllerImpl sprintController;

    private static final AddNewSprintData ADD_SPRINT_DATA = SprintTestDataBuilder.addNewProjectData();

    private final SprintMapper MAPPER = Mappers.getMapper(SprintMapper.class);

    @Mock
    private SprintService sprintService;

    @Test
    final void test_addNewSprint_shouldAddNewSprint() {
        // given
        SprintResponse expected = MAPPER.toSprintResponse(ADD_SPRINT_DATA.asSprint());

        // when
        when(sprintService.addNewSprint(ADD_SPRINT_DATA.name(), ADD_SPRINT_DATA.startDate(),
                ADD_SPRINT_DATA.endDate(), ADD_SPRINT_DATA.description(), ADD_SPRINT_DATA.project().getOwner()))
                .thenReturn(expected);

        SprintResponse actual = sprintController.addNewSprint(ADD_SPRINT_DATA.asSprintRequest(), ADD_SPRINT_DATA.project().getOwner());

        // then
        assertEquals(expected, actual, SPRINT_RESPONSE_SHOULD_BE_EQUAL_TO_EXPECTED);
    }

    @Test
    final void test_getSprintById_shouldGetSprint() {
        // given
        SprintResponse expected = MAPPER.toSprintResponse(ADD_SPRINT_DATA.asSprint());

        // when
        when(sprintService.getSprintById(ADD_SPRINT_DATA.asSprint().getSprintId(), ADD_SPRINT_DATA.project().getOwner()))
                .thenReturn(expected);

        SprintResponse actual = sprintController.getSprintById(ADD_SPRINT_DATA.asSprint().getSprintId(), ADD_SPRINT_DATA.project().getOwner());

        // then
        assertEquals(expected, actual, SPRINT_RESPONSE_SHOULD_BE_EQUAL_TO_EXPECTED);
    }

    @Test
    final void test_getSprintsOnPage_shouldGetSprints() {
        // given
        List<SprintResponse> expected = List.of(MAPPER.toSprintResponse(ADD_SPRINT_DATA.asSprint()));
        int page = 0;

        // when
        when(sprintService.getSprintsOnPage(page, ADD_SPRINT_DATA.project().getProjectId(),
                ADD_SPRINT_DATA.project().getOwner()))
                .thenReturn(expected);

        List<SprintResponse> actual = sprintController.getSprintsOnPage(page, ADD_SPRINT_DATA.project().getProjectId(),
                ADD_SPRINT_DATA.project().getOwner());

        // then
        assertEquals(expected, actual, SPRINT_RESPONSE_SHOULD_BE_EQUAL_TO_EXPECTED);
    }

    @Test
    final void test_updateSprintsName_shouldUpdateName() {
        // given
        SprintResponse expected = MAPPER.toSprintResponse(ADD_SPRINT_DATA.asSprint());

        // when
        when(sprintService.updateSprintsName(ADD_SPRINT_DATA.name(),
                ADD_SPRINT_DATA.asSprint().getSprintId(), ADD_SPRINT_DATA.project().getOwner()))
                .thenReturn(expected);

        SprintResponse actual = sprintController.updateSprintsName(ADD_SPRINT_DATA.name(),
                ADD_SPRINT_DATA.asSprint().getSprintId(), ADD_SPRINT_DATA.project().getOwner());

        // then
        assertEquals(expected, actual, SPRINT_RESPONSE_SHOULD_BE_EQUAL_TO_EXPECTED);
    }

    @Test
    final void test_updateSprintsDescription_shouldUpdateDescription() {
        // given
        SprintResponse expected = MAPPER.toSprintResponse(ADD_SPRINT_DATA.asSprint());

        // when
        when(sprintService.updateSprintsDescription(ADD_SPRINT_DATA.description(),
                ADD_SPRINT_DATA.asSprint().getSprintId(), ADD_SPRINT_DATA.project().getOwner()))
                .thenReturn(expected);

        SprintResponse actual = sprintController.updateSprintsDescription(ADD_SPRINT_DATA.description(),
                ADD_SPRINT_DATA.asSprint().getSprintId(), ADD_SPRINT_DATA.project().getOwner());

        // then
        assertEquals(expected, actual, SPRINT_RESPONSE_SHOULD_BE_EQUAL_TO_EXPECTED);
    }

    @Test
    final void test_updateSprintsStartDate_shouldUpdateStartDate() {
        // given
        SprintResponse expected = MAPPER.toSprintResponse(ADD_SPRINT_DATA.asSprint());

        // when
        when(sprintService.updateSprintsStartDate(ADD_SPRINT_DATA.startDate(),
                ADD_SPRINT_DATA.asSprint().getSprintId(), ADD_SPRINT_DATA.project().getOwner()))
                .thenReturn(expected);

        SprintResponse actual = sprintController.updateSprintsStartDate(ADD_SPRINT_DATA.startDate(),
                ADD_SPRINT_DATA.asSprint().getSprintId(), ADD_SPRINT_DATA.project().getOwner());

        // then
        assertEquals(expected, actual, SPRINT_RESPONSE_SHOULD_BE_EQUAL_TO_EXPECTED);
    }

    @Test
    final void test_updateSprintsEndDate_shouldUpdateEndDate() {
        // given
        SprintResponse expected = MAPPER.toSprintResponse(ADD_SPRINT_DATA.asSprint());

        // when
        when(sprintService.updateSprintsEndDate(ADD_SPRINT_DATA.endDate(),
                ADD_SPRINT_DATA.asSprint().getSprintId(), ADD_SPRINT_DATA.project().getOwner()))
                .thenReturn(expected);

        SprintResponse actual = sprintController.updateSprintsEndDate(ADD_SPRINT_DATA.endDate(),
                ADD_SPRINT_DATA.asSprint().getSprintId(), ADD_SPRINT_DATA.project().getOwner());

        // then
        assertEquals(expected, actual, SPRINT_RESPONSE_SHOULD_BE_EQUAL_TO_EXPECTED);
    }

    @Test
    final void test_deleteSprint_shouldDeleteSprint() {
        // given
        SprintResponse expected = MAPPER.toSprintResponse(ADD_SPRINT_DATA.asSprint());
        long sprintId = ADD_SPRINT_DATA.asSprint().getSprintId();

        // when
        when(sprintService.deleteSprint(sprintId, ADD_SPRINT_DATA.project().getOwner()))
                .thenReturn(expected);

        SprintResponse actual = sprintController.deleteSprint(sprintId, ADD_SPRINT_DATA.project().getOwner());

        // then
        assertEquals(expected, actual, SPRINT_RESPONSE_SHOULD_BE_EQUAL_TO_EXPECTED);
    }
}