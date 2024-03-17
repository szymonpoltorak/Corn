package dev.corn.cornbackend.api.backlog.comment;

import dev.corn.cornbackend.api.backlog.comment.data.BacklogItemCommentResponse;
import dev.corn.cornbackend.entities.backlog.comment.interfaces.BacklogItemCommentMapper;
import dev.corn.cornbackend.entities.backlog.comment.interfaces.BacklogItemCommentRepository;
import dev.corn.cornbackend.entities.backlog.item.interfaces.BacklogItemRepository;
import dev.corn.cornbackend.test.backlog.item.comment.BacklogItemCommentTestDataBuilder;
import dev.corn.cornbackend.test.backlog.item.comment.data.BacklogItemCommentTestData;
import dev.corn.cornbackend.test.backlog.item.comment.data.UpdateBacklogItemCommentTestData;
import dev.corn.cornbackend.utils.exceptions.backlog.item.BacklogItemNotFoundException;
import dev.corn.cornbackend.utils.exceptions.backlog.item.comment.BacklogItemCommentNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class BacklogItemCommentServiceImplTest {

    @InjectMocks
    BacklogItemCommentServiceImpl backlogItemCommentServiceImpl;

    @Mock
    BacklogItemCommentRepository backlogItemCommentRepository;

    @Mock
    BacklogItemRepository backlogItemRepository;

    @Mock
    BacklogItemCommentMapper backlogItemCommentMapper;

    private final BacklogItemCommentTestData BACKLOG_ITEM_COMMENT_TEST_DATA = BacklogItemCommentTestDataBuilder.backlogItemCommentTestData();
    private final UpdateBacklogItemCommentTestData UPDATE_BACKLOG_ITEM_TEST_DATA = BacklogItemCommentTestDataBuilder.updateBacklogItemCommentTestData();

    private static final String SHOULD_RETURN_CORRECT_COMMENT_RESPONSE = "Should return correct comment response";
    private static final String SHOULD_THROW = "Should throw BacklogItemNotFoundException on incorrect backlogItem";

    @Test
    final void test_addNewCommentShouldReturnCorrectBacklogItemCommentResponse() {
        //given

        //when
        when(backlogItemRepository.findByIdWithProjectMember(BACKLOG_ITEM_COMMENT_TEST_DATA.backlogItemCommentRequest().backlogItemId(),
                BACKLOG_ITEM_COMMENT_TEST_DATA.user()))
                .thenReturn(Optional.of(BACKLOG_ITEM_COMMENT_TEST_DATA.backlogItemComment().getBacklogItem()));

        when(backlogItemCommentRepository.save(BACKLOG_ITEM_COMMENT_TEST_DATA.backlogItemCommentToSave()))
                .thenReturn(BACKLOG_ITEM_COMMENT_TEST_DATA.backlogItemComment());

        when(backlogItemCommentMapper.toBacklogItemCommentResponse(BACKLOG_ITEM_COMMENT_TEST_DATA.backlogItemComment()))
                .thenReturn(BACKLOG_ITEM_COMMENT_TEST_DATA.backlogItemCommentResponse());

        BacklogItemCommentResponse expected = BACKLOG_ITEM_COMMENT_TEST_DATA.backlogItemCommentResponse();

        //then
        assertEquals(expected, backlogItemCommentServiceImpl.addNewComment(BACKLOG_ITEM_COMMENT_TEST_DATA.backlogItemCommentRequest(), BACKLOG_ITEM_COMMENT_TEST_DATA.user()),
                SHOULD_RETURN_CORRECT_COMMENT_RESPONSE);
    }

    @Test
    final void test_addNewCommentShouldThrowBacklogItemNotFoundExceptionOnIncorrectBacklogItem() {
        //given

        //when
        when(backlogItemRepository.findByIdWithProjectMember(BACKLOG_ITEM_COMMENT_TEST_DATA.backlogItemCommentRequest().backlogItemId(),
                BACKLOG_ITEM_COMMENT_TEST_DATA.user()))
                .thenReturn(Optional.empty());

        //then
        assertThrows(BacklogItemNotFoundException.class, () -> backlogItemCommentServiceImpl.addNewComment(BACKLOG_ITEM_COMMENT_TEST_DATA.backlogItemCommentRequest(), BACKLOG_ITEM_COMMENT_TEST_DATA.user()),
                SHOULD_THROW);
    }

    @Test
    final void test_updateBacklogItemComentShouldReturnCorrectBacklogItemCommentResponse() {
        //given
        long commentId = 1L;

        //when
        when(backlogItemCommentRepository.findByBacklogItemCommentIdAndUser(commentId, UPDATE_BACKLOG_ITEM_TEST_DATA.user()))
                .thenReturn(Optional.of(UPDATE_BACKLOG_ITEM_TEST_DATA.backlogItemComment()));

        when(backlogItemCommentRepository.save(UPDATE_BACKLOG_ITEM_TEST_DATA.updatedBacklogItemComment()))
                .thenReturn(UPDATE_BACKLOG_ITEM_TEST_DATA.updatedBacklogItemComment());

        when(backlogItemCommentMapper.toBacklogItemCommentResponse(UPDATE_BACKLOG_ITEM_TEST_DATA.updatedBacklogItemComment()))
                .thenReturn(UPDATE_BACKLOG_ITEM_TEST_DATA.backlogItemCommentResponse());

        BacklogItemCommentResponse expected = UPDATE_BACKLOG_ITEM_TEST_DATA.backlogItemCommentResponse();

        //then
        assertEquals(expected, backlogItemCommentServiceImpl.updateComment(commentId, UPDATE_BACKLOG_ITEM_TEST_DATA.newComment(), BACKLOG_ITEM_COMMENT_TEST_DATA.user()),
                SHOULD_RETURN_CORRECT_COMMENT_RESPONSE);
    }

    @Test
    final void test_updateShouldThrowBacklogItemNotFoundExceptionOnIncorrectBacklogItem() {
        //given
        long commentId = 1L;

        //when
        when(backlogItemCommentRepository.findByBacklogItemCommentIdAndUser(commentId, BACKLOG_ITEM_COMMENT_TEST_DATA.user()))
                .thenReturn(Optional.empty());

        //then
        assertThrows(BacklogItemCommentNotFoundException.class, () ->
                        backlogItemCommentServiceImpl.updateComment(commentId,
                                UPDATE_BACKLOG_ITEM_TEST_DATA.newComment(),
                                BACKLOG_ITEM_COMMENT_TEST_DATA.user()),
                SHOULD_THROW);
    }

    @Test
    final void test_deleteCommentShouldReturnCorrectBacklogItemCommentResponse() {
        //given
        long commentId = 1L;

        //when
        when(backlogItemCommentRepository.findByIdWithUserOrOwner(commentId, UPDATE_BACKLOG_ITEM_TEST_DATA.user()))
                .thenReturn(Optional.of(BACKLOG_ITEM_COMMENT_TEST_DATA.backlogItemComment()));

        when(backlogItemCommentMapper.toBacklogItemCommentResponse(BACKLOG_ITEM_COMMENT_TEST_DATA.backlogItemComment()))
                .thenReturn(BACKLOG_ITEM_COMMENT_TEST_DATA.backlogItemCommentResponse());

        BacklogItemCommentResponse expected = BACKLOG_ITEM_COMMENT_TEST_DATA.backlogItemCommentResponse();

        //then
        assertEquals(expected, backlogItemCommentServiceImpl.deleteComment(commentId, UPDATE_BACKLOG_ITEM_TEST_DATA.user()),
                SHOULD_RETURN_CORRECT_COMMENT_RESPONSE);
    }

    @Test
    final void test_deleteCommentShouldThrowBacklogItemNotFoundExceptionOnIncorrectBacklogItem() {
        //given
        long commentId = 1L;

        //when
        when(backlogItemCommentRepository.findByIdWithUserOrOwner(commentId, UPDATE_BACKLOG_ITEM_TEST_DATA.user()))
                .thenReturn(Optional.empty());

        //then
        assertThrows(BacklogItemCommentNotFoundException.class, () ->
                        backlogItemCommentServiceImpl.deleteComment(commentId,
                                UPDATE_BACKLOG_ITEM_TEST_DATA.user()),
                SHOULD_THROW);
    }

    @Test
    final void test_getCommentShouldReturnCorrectBacklogItemCommentResponse() {
        //given
        long commentId = 1L;

        //when
        when(backlogItemCommentRepository.findByIdWithProjectMember(commentId, UPDATE_BACKLOG_ITEM_TEST_DATA.user()))
                .thenReturn(Optional.of(BACKLOG_ITEM_COMMENT_TEST_DATA.backlogItemComment()));

        when(backlogItemCommentMapper.toBacklogItemCommentResponse(BACKLOG_ITEM_COMMENT_TEST_DATA.backlogItemComment()))
                .thenReturn(BACKLOG_ITEM_COMMENT_TEST_DATA.backlogItemCommentResponse());

        BacklogItemCommentResponse expected = BACKLOG_ITEM_COMMENT_TEST_DATA.backlogItemCommentResponse();

        //then
        assertEquals(expected, backlogItemCommentServiceImpl.getComment(commentId,
                        UPDATE_BACKLOG_ITEM_TEST_DATA.user()),
                SHOULD_RETURN_CORRECT_COMMENT_RESPONSE);
    }

    @Test
    final void test_getCommentShouldThrowBacklogItemNotFoundExceptionOnIncorrectBacklogItem() {
        //given
        long commentId = 1L;

        //when
        when(backlogItemCommentRepository.findByIdWithProjectMember(commentId, UPDATE_BACKLOG_ITEM_TEST_DATA.user()))
                .thenReturn(Optional.empty());

        //then
        assertThrows(BacklogItemCommentNotFoundException.class, () ->
                        backlogItemCommentServiceImpl.getComment(commentId,
                                UPDATE_BACKLOG_ITEM_TEST_DATA.user()),
                SHOULD_THROW);
    }
}