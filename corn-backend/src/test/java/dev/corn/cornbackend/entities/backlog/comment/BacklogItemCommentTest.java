package dev.corn.cornbackend.entities.backlog.comment;

import dev.corn.cornbackend.entities.backlog.comment.constants.BacklogItemCommentConstants;
import dev.corn.cornbackend.entities.backlog.item.BacklogItem;
import dev.corn.cornbackend.entities.user.User;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = LocalValidatorFactoryBean.class)
class BacklogItemCommentTest {

    @Autowired
    private LocalValidatorFactoryBean validator;

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " \n\t\r"})
    final void test_shouldReturnBlankCommentViolationOnNullEmptyOrOnlyWhiteSpaceComment(String comment) {
        // given
        BacklogItemComment backlogItemComment = new BacklogItemComment();
        backlogItemComment.setComment(comment);

        // when
        Set<ConstraintViolation<BacklogItemComment>> violations = validator.validateProperty(
                backlogItemComment,
                BacklogItemCommentConstants.BACKLOG_ITEM_COMMENT_COMMENT_FIELD_NAME);

        // then
        String expectedMessage = BacklogItemCommentConstants.BACKLOG_ITEM_COMMENT_BLANK_COMMENT_MSG;

        assertEquals(1, violations.size());
        assertEquals(expectedMessage, violations.iterator().next().getMessage());
    }

    @Test
    final void test_shouldReturnSizeViolationOnCommentWithMoreThan500Characters() {
        // given
        BacklogItemComment backlogItemComment = new BacklogItemComment();
        backlogItemComment.setComment("a".repeat(501));

        // when
        Set<ConstraintViolation<BacklogItemComment>> violations = validator.validateProperty(
                backlogItemComment,
                BacklogItemCommentConstants.BACKLOG_ITEM_COMMENT_COMMENT_FIELD_NAME);

        // then
        String expectedMessage = BacklogItemCommentConstants.BACKLOG_ITEM_COMMENT_WRONG_SIZE_COMMENT_MSG;

        assertEquals(1, violations.size());
        assertEquals(expectedMessage, violations.iterator().next().getMessage());
    }

    @Test
    final void test_shouldReturnNoViolationsWhenCreatingCommentWithCorrectComment() {
        // given
        BacklogItemComment backlogItemComment = new BacklogItemComment();
        backlogItemComment.setComment("comment");

        // when
        Set<ConstraintViolation<BacklogItemComment>> violations = validator.validateProperty(
                backlogItemComment,
                BacklogItemCommentConstants.BACKLOG_ITEM_COMMENT_COMMENT_FIELD_NAME);

        // then
        assertEquals(0, violations.size());
    }

    @Test
    final void test_shouldReturnNullUserViolationWhenCreatingCommentWithNullUser() {
        // given
        BacklogItemComment backlogItemComment = new BacklogItemComment();
        backlogItemComment.setUser(null);

        // when
        Set<ConstraintViolation<BacklogItemComment>> violations = validator.validateProperty(
                backlogItemComment,
                BacklogItemCommentConstants.BACKLOG_ITEM_COMMENT_USER_FIELD_NAME);

        // then
        String expectedMessage = BacklogItemCommentConstants.BACKLOG_ITEM_COMMENT_NULL_USER_MSG;

        assertEquals(1, violations.size());
        assertEquals(expectedMessage, violations.iterator().next().getMessage());
    }

    @Test
    final void test_shouldReturnNoViolationsWhenCreatingCommentWithCorrectUser() {
        // given
        BacklogItemComment backlogItemComment = new BacklogItemComment();
        backlogItemComment.setUser(new User());

        // when
        Set<ConstraintViolation<BacklogItemComment>> violations = validator.validateProperty(
                backlogItemComment,
                BacklogItemCommentConstants.BACKLOG_ITEM_COMMENT_USER_FIELD_NAME);

        // then
        assertEquals(0, violations.size());
    }

    @Test
    final void test_ShouldReturnNullBacklogItemViolationWhenCreatingCommentWithNullBacklogItem() {
        // given
        BacklogItemComment backlogItemComment = new BacklogItemComment();
        backlogItemComment.setBacklogItem(null);

        // when
        Set<ConstraintViolation<BacklogItemComment>> violations = validator.validateProperty(
                backlogItemComment,
                BacklogItemCommentConstants.BACKLOG_ITEM_COMMENT_BACKLOG_ITEM_FIELD_NAME);

        // then
        String expectedMessage = BacklogItemCommentConstants.BACKLOG_ITEM_COMMENT_NULL_BACK_LOG_ITEM_MSG;

        assertEquals(1, violations.size());
        assertEquals(expectedMessage, violations.iterator().next().getMessage());
    }

    @Test
    final void test_ShouldReturnNoViolationsWhenCreatingCommentWithCorrectBacklogItem() {
        // given
        BacklogItemComment backlogItemComment = new BacklogItemComment();
        backlogItemComment.setBacklogItem(new BacklogItem());

        // when
        Set<ConstraintViolation<BacklogItemComment>> violations = validator.validateProperty(
                backlogItemComment,
                BacklogItemCommentConstants.BACKLOG_ITEM_COMMENT_BACKLOG_ITEM_FIELD_NAME);

        // then
        assertEquals(0, violations.size());
    }


}