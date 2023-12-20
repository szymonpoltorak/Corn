package dev.corn.cornbackend.api.backlog.item;

import dev.corn.cornbackend.api.backlog.item.data.BacklogItemDetails;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemResponse;
import dev.corn.cornbackend.api.backlog.item.interfaces.BacklogItemService;
import dev.corn.cornbackend.test.backlog.item.BacklogItemTestDataBuilder;
import dev.corn.cornbackend.test.backlog.item.data.AddBacklogItemTestData;
import dev.corn.cornbackend.test.backlog.item.data.BacklogItemDetailsTestData;
import dev.corn.cornbackend.test.backlog.item.data.BacklogItemListTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BacklogItemControllerImplTest {

    @InjectMocks
    private BacklogItemControllerImpl backlogItemController;

    @Mock
    private BacklogItemService backlogItemService;

    private final AddBacklogItemTestData ADD_BACKLOG_ITEM_DATA = BacklogItemTestDataBuilder.addBacklogItemTestData();
    private final BacklogItemListTestData BACKLOG_ITEM_LIST_TEST_DATA = BacklogItemTestDataBuilder.backlogItemListTestData();
    private final BacklogItemDetailsTestData BACKLOG_ITEM_DETAILS_TEST_DATA = BacklogItemTestDataBuilder.backlogItemDetailsTestData();

    private final static String SHOULD_RETURN_CORRECT_BACKLOG_ITEM_RESPONSE = "Should return correct BacklogItemResponse";
    @Test
    final void test_createShouldReturnCorrectBacklogItemResponse() {
        //given
        long id = 1L;

        //when
        when(backlogItemService.create(ADD_BACKLOG_ITEM_DATA.backlogItemRequest()))
                .thenReturn(ADD_BACKLOG_ITEM_DATA.backlogItemResponse());

        BacklogItemResponse expected = ADD_BACKLOG_ITEM_DATA.backlogItemResponse();

        //then
        assertEquals(expected, backlogItemController.create(ADD_BACKLOG_ITEM_DATA.backlogItemRequest()),
               SHOULD_RETURN_CORRECT_BACKLOG_ITEM_RESPONSE);
    }

    @Test
    final void test_getByIdShouldReturnCorrectBacklogItemResponse() {
        //given
        long id = 1L;

        //when
        when(backlogItemService.getById(id))
                .thenReturn(ADD_BACKLOG_ITEM_DATA.backlogItemResponse());

        BacklogItemResponse expected = ADD_BACKLOG_ITEM_DATA.backlogItemResponse();

        //then
        assertEquals(expected, backlogItemController.getById(id),
               SHOULD_RETURN_CORRECT_BACKLOG_ITEM_RESPONSE);
    }

    @Test
    final void test_updateShouldReturnCorrectBacklogItemResponse() {
        //given
        long id = 1L;

        //when
        when(backlogItemService.update(id, ADD_BACKLOG_ITEM_DATA.backlogItemRequest()))
                .thenReturn(ADD_BACKLOG_ITEM_DATA.backlogItemResponse());

        BacklogItemResponse expected = ADD_BACKLOG_ITEM_DATA.backlogItemResponse();

        //then
        assertEquals(expected, backlogItemController.update(id, ADD_BACKLOG_ITEM_DATA.backlogItemRequest()),
               SHOULD_RETURN_CORRECT_BACKLOG_ITEM_RESPONSE);
    }

    @Test
    final void test_deleteByIdShouldReturnCorrectBacklogItemResponse() {
        //given
        long id = 1L;

        //when
        when(backlogItemService.deleteById(id))
                .thenReturn(ADD_BACKLOG_ITEM_DATA.backlogItemResponse());

        BacklogItemResponse expected = ADD_BACKLOG_ITEM_DATA.backlogItemResponse();

        //then
        assertEquals(expected, backlogItemController.deleteById(id),
               SHOULD_RETURN_CORRECT_BACKLOG_ITEM_RESPONSE);
    }

    @Test
    final void test_getBySprintIdShouldReturnCorrectBacklogItemResponse() {
        //given
        long sprintId = 1L;

        //when
        when(backlogItemService.getBySprintId(sprintId))
                .thenReturn(BACKLOG_ITEM_LIST_TEST_DATA.backlogItemResponses());

        List<BacklogItemResponse> expected = BACKLOG_ITEM_LIST_TEST_DATA.backlogItemResponses();

        //then
        assertEquals(expected, backlogItemController.getBySprintId(sprintId),
               SHOULD_RETURN_CORRECT_BACKLOG_ITEM_RESPONSE);
    }

    @Test
    final void test_getByProjectIdShouldReturnCorrectBacklogItemResponse() {
        //given
        long projectId = 1L;

        //when
        when(backlogItemService.getByProjectId(projectId))
                .thenReturn(BACKLOG_ITEM_LIST_TEST_DATA.backlogItemResponses());

        List<BacklogItemResponse> expected = BACKLOG_ITEM_LIST_TEST_DATA.backlogItemResponses();

        //then
        assertEquals(expected, backlogItemController.getByProjectId(projectId),
               SHOULD_RETURN_CORRECT_BACKLOG_ITEM_RESPONSE);
    }

    @Test
    final void test_getDetailsByIdShouldReturnCorrectBacklogItemDetails() {
        //given
        long id = 1L;

        //when
        when(backlogItemService.getDetailsById(id))
                .thenReturn(BACKLOG_ITEM_DETAILS_TEST_DATA.backlogItemDetails());

        BacklogItemDetails expected = BACKLOG_ITEM_DETAILS_TEST_DATA.backlogItemDetails();

        //then
        assertEquals(expected, backlogItemController.getDetailsById(id),
               SHOULD_RETURN_CORRECT_BACKLOG_ITEM_RESPONSE);
    }



}