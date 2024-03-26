package dev.corn.cornbackend.api.backlog.item;

import dev.corn.cornbackend.api.backlog.item.data.BacklogItemDetails;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemResponse;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemResponseList;
import dev.corn.cornbackend.api.backlog.item.interfaces.BacklogItemService;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.test.backlog.item.BacklogItemTestDataBuilder;
import dev.corn.cornbackend.test.backlog.item.data.AddBacklogItemTestData;
import dev.corn.cornbackend.test.backlog.item.data.BacklogItemDetailsTestData;
import dev.corn.cornbackend.test.backlog.item.data.BacklogItemListTestData;
import dev.corn.cornbackend.test.user.UserTestDataBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    private final User SAMPLE_USER = UserTestDataBuilder.createSampleUser();

    private final static String SHOULD_RETURN_CORRECT_BACKLOG_ITEM_RESPONSE = "Should return correct BacklogItemResponse";
    @Test
    final void test_createShouldReturnCorrectBacklogItemResponse() {
        //given
        long id = 1L;

        //when
        when(backlogItemService.create(ADD_BACKLOG_ITEM_DATA.backlogItemRequest(), SAMPLE_USER))
                .thenReturn(ADD_BACKLOG_ITEM_DATA.backlogItemResponse());

        BacklogItemResponse expected = ADD_BACKLOG_ITEM_DATA.backlogItemResponse();

        //then
        assertEquals(expected, backlogItemController.create(ADD_BACKLOG_ITEM_DATA.backlogItemRequest(), SAMPLE_USER),
               SHOULD_RETURN_CORRECT_BACKLOG_ITEM_RESPONSE);
    }

    @Test
    final void test_getByIdShouldReturnCorrectBacklogItemResponse() {
        //given
        long id = 1L;

        //when
        when(backlogItemService.getById(id, SAMPLE_USER))
                .thenReturn(ADD_BACKLOG_ITEM_DATA.backlogItemResponse());

        BacklogItemResponse expected = ADD_BACKLOG_ITEM_DATA.backlogItemResponse();

        //then
        assertEquals(expected, backlogItemController.getById(id, SAMPLE_USER),
               SHOULD_RETURN_CORRECT_BACKLOG_ITEM_RESPONSE);
    }

    @Test
    final void test_updateShouldReturnCorrectBacklogItemResponse() {
        //given
        long id = 1L;

        //when
        when(backlogItemService.update(id, ADD_BACKLOG_ITEM_DATA.backlogItemRequest(), SAMPLE_USER))
                .thenReturn(ADD_BACKLOG_ITEM_DATA.backlogItemResponse());

        BacklogItemResponse expected = ADD_BACKLOG_ITEM_DATA.backlogItemResponse();

        //then
        assertEquals(expected, backlogItemController.update(id, ADD_BACKLOG_ITEM_DATA.backlogItemRequest(), SAMPLE_USER),
               SHOULD_RETURN_CORRECT_BACKLOG_ITEM_RESPONSE);
    }

    @Test
    final void test_deleteByIdShouldReturnCorrectBacklogItemResponse() {
        //given
        long id = 1L;

        //when
        when(backlogItemService.deleteById(id, SAMPLE_USER))
                .thenReturn(ADD_BACKLOG_ITEM_DATA.backlogItemResponse());

        BacklogItemResponse expected = ADD_BACKLOG_ITEM_DATA.backlogItemResponse();

        //then
        assertEquals(expected, backlogItemController.deleteById(id, SAMPLE_USER),
               SHOULD_RETURN_CORRECT_BACKLOG_ITEM_RESPONSE);
    }

    @Test
    final void test_getBySprintIdShouldReturnCorrectBacklogItemResponse() {
        //given
        long sprintId = 1L;
        int pageNumber = 0;
        String sortBy = "";
        String orderBy = "";

        //when
        when(backlogItemService.getBySprintId(sprintId, pageNumber, sortBy, orderBy, SAMPLE_USER))
                .thenReturn(BACKLOG_ITEM_LIST_TEST_DATA.backlogItemResponseList());

        BacklogItemResponseList expected = BACKLOG_ITEM_LIST_TEST_DATA.backlogItemResponseList();

        //then
        assertEquals(expected, backlogItemController.getBySprintId(sprintId, pageNumber, sortBy, orderBy, SAMPLE_USER),
               SHOULD_RETURN_CORRECT_BACKLOG_ITEM_RESPONSE);
    }

    @Test
    final void test_getByProjectIdShouldReturnCorrectBacklogItemResponse() {
        //given
        long projectId = 1L;
        int pageNumber = 0;
        String sortBy = "";
        String orderBy = "";

        //when
        when(backlogItemService.getByProjectId(projectId, pageNumber, sortBy, orderBy, SAMPLE_USER))
                .thenReturn(BACKLOG_ITEM_LIST_TEST_DATA.backlogItemResponseList());

        BacklogItemResponseList expected = BACKLOG_ITEM_LIST_TEST_DATA.backlogItemResponseList();

        //then
        assertEquals(expected, backlogItemController.getByProjectId(projectId, pageNumber, sortBy, orderBy, SAMPLE_USER),
               SHOULD_RETURN_CORRECT_BACKLOG_ITEM_RESPONSE);
    }

    @Test
    final void test_getDetailsByIdShouldReturnCorrectBacklogItemDetails() {
        //given
        long id = 1L;

        //when
        when(backlogItemService.getDetailsById(id, SAMPLE_USER))
                .thenReturn(BACKLOG_ITEM_DETAILS_TEST_DATA.backlogItemDetails());

        BacklogItemDetails expected = BACKLOG_ITEM_DETAILS_TEST_DATA.backlogItemDetails();

        //then
        assertEquals(expected, backlogItemController.getDetailsById(id, SAMPLE_USER),
               SHOULD_RETURN_CORRECT_BACKLOG_ITEM_RESPONSE);
    }

    @Test
    final void test_getAllWithoutSprintShouldReturnCorrectBacklogItemResponse() {
        //given
        long projectId = 1L;
        int pageNumber = 0;
        String sortBy = "";
        String orderBy = "";

        //when
        when(backlogItemService.getAllWithoutSprint(projectId, pageNumber, sortBy, orderBy, SAMPLE_USER))
                .thenReturn(BACKLOG_ITEM_LIST_TEST_DATA.backlogItemResponseList());

        BacklogItemResponseList expected = BACKLOG_ITEM_LIST_TEST_DATA.backlogItemResponseList();

        //then
        assertEquals(expected, backlogItemController.getAllWithoutSprint(projectId, pageNumber, sortBy, orderBy, SAMPLE_USER),
                SHOULD_RETURN_CORRECT_BACKLOG_ITEM_RESPONSE);
    }

}