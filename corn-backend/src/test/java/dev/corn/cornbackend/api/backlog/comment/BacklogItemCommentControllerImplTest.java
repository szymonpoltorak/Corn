package dev.corn.cornbackend.api.backlog.comment;

import dev.corn.cornbackend.api.backlog.comment.data.BacklogItemCommentResponse;
import dev.corn.cornbackend.api.backlog.comment.data.BacklogItemCommentResponseList;
import dev.corn.cornbackend.api.backlog.comment.interfaces.BacklogItemCommentService;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.test.backlog.item.comment.BacklogItemCommentTestDataBuilder;
import dev.corn.cornbackend.test.backlog.item.comment.data.BacklogItemCommentTestData;
import dev.corn.cornbackend.test.backlog.item.comment.data.UpdateBacklogItemCommentTestData;
import dev.corn.cornbackend.test.user.UserTestDataBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BacklogItemCommentControllerImplTest {


    @InjectMocks
    BacklogItemCommentControllerImpl backlogItemCommentController;

    @Mock
    BacklogItemCommentService backlogItemCommentService;

    private final BacklogItemCommentTestData BACKLOG_ITEM_COMMENT_TEST_DATA = BacklogItemCommentTestDataBuilder.backlogItemCommentTestData();
    private final UpdateBacklogItemCommentTestData UPDATE_BACKLOG_ITEM_TEST_DATA = BacklogItemCommentTestDataBuilder.updateBacklogItemCommentTestData();
    private final User SAMPLE_USER = UserTestDataBuilder.createSampleUser();

    private static final String SHOULD_RETURN_CORRECT_COMMENT_RESPONSE = "Should return correct comment response";


    @Test
    final void test_addNewCommentShouldReturnCorrectCommentResponseForGivenRequest() {
        //given

        //when
        when(backlogItemCommentService.addNewComment(
                BACKLOG_ITEM_COMMENT_TEST_DATA.backlogItemCommentRequest(),
                BACKLOG_ITEM_COMMENT_TEST_DATA.user()))
                .thenReturn(BACKLOG_ITEM_COMMENT_TEST_DATA.backlogItemCommentResponse());

        BacklogItemCommentResponse expected = BACKLOG_ITEM_COMMENT_TEST_DATA.backlogItemCommentResponse();

        //then
        assertEquals(expected, backlogItemCommentController.addNewComment(
                BACKLOG_ITEM_COMMENT_TEST_DATA.backlogItemCommentRequest(),
                BACKLOG_ITEM_COMMENT_TEST_DATA.user()),
                SHOULD_RETURN_CORRECT_COMMENT_RESPONSE);
    }

    @Test
    final void test_updateCommentShouldReturnCorrectCommentResponseForGivenRequest() {
        //given
        long commentId = 1L;

        //when
        when(backlogItemCommentService.updateComment(
                commentId,
                UPDATE_BACKLOG_ITEM_TEST_DATA.newComment(),
                SAMPLE_USER))
                .thenReturn(UPDATE_BACKLOG_ITEM_TEST_DATA.backlogItemCommentResponse());

        BacklogItemCommentResponse expected = UPDATE_BACKLOG_ITEM_TEST_DATA.backlogItemCommentResponse();

        //then
        assertEquals(expected, backlogItemCommentController.updateComment(
                commentId,
                UPDATE_BACKLOG_ITEM_TEST_DATA.newComment(),
                        SAMPLE_USER),
                SHOULD_RETURN_CORRECT_COMMENT_RESPONSE);
    }

    @Test
    final void test_deleteCommentShouldReturnCorrectCommentResponseForGivenRequest() {
        //given
        long commentId = 1L;

        //when
        when(backlogItemCommentService.deleteComment(commentId, SAMPLE_USER))
                .thenReturn(BACKLOG_ITEM_COMMENT_TEST_DATA.backlogItemCommentResponse());

        BacklogItemCommentResponse expected = BACKLOG_ITEM_COMMENT_TEST_DATA.backlogItemCommentResponse();

        //then
        assertEquals(expected, backlogItemCommentController.deleteComment(commentId, SAMPLE_USER),
                SHOULD_RETURN_CORRECT_COMMENT_RESPONSE);
    }

    @Test
    final void test_getCommentShouldReturnCorrectCommentResponseForGivenRequest() {
        //given
        long commentId = 1L;

        //when
        when(backlogItemCommentService.getComment(commentId, SAMPLE_USER))
                .thenReturn(BACKLOG_ITEM_COMMENT_TEST_DATA.backlogItemCommentResponse());

        BacklogItemCommentResponse expected = BACKLOG_ITEM_COMMENT_TEST_DATA.backlogItemCommentResponse();

        //then
        assertEquals(expected, backlogItemCommentController.getComment(commentId, SAMPLE_USER),
                SHOULD_RETURN_CORRECT_COMMENT_RESPONSE);
    }

    @Test
    final void test_getCommentsForBacklogItemShouldReturnCorrectBacklogItemCommentsResponseList() {
        //given
        long backlogItemId = 1l;
        int pageNumber = 0;

        //when
        when(backlogItemCommentService.getCommentsForBacklogItem(backlogItemId, pageNumber, SAMPLE_USER))
                .thenReturn(BACKLOG_ITEM_COMMENT_TEST_DATA.backlogItemCommentResponseList());

        BacklogItemCommentResponseList expected = BACKLOG_ITEM_COMMENT_TEST_DATA.backlogItemCommentResponseList();

        //then
        assertEquals(expected, backlogItemCommentController.getCommentsForBacklogItem(backlogItemId, pageNumber, SAMPLE_USER),
                "Should return correct BacklogItemCommentResponseList");
    }

}