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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Set;



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

    @Test
    final void testEquals_SameProject_ReturnsTrue() {
        // Given
        BacklogItemComment item = new BacklogItemComment();

        item.setBacklogItemCommentId(0L);

        // When-Then
        assertEquals(item, item, "Backlog items should be equal");
    }

    @Test
    final void testEquals_DifferentUsers() {
        // Given
        BacklogItemComment item1 = createSampleBacklogItemComment();
        BacklogItemComment item2 = createSampleBacklogItemComment();

        item2.setUser(User.builder().username("dasdas").build());

        // When-Then
        assertNotEquals(item1, item2, "Backlog items should not be equal");
    }

    @Test
    final void test_toString_ReturnsValidString() {
        // Given
        BacklogItemComment item = new BacklogItemComment();

        // When
        String string = item.toString();

        // Then
        assertNotNull(string, "String should not be null");
    }

    @Test
    final void testEquals_not_instance_of_project() {
        // Given
        BacklogItemComment item = new BacklogItemComment();
        Object object = new Object();

        // When-Then
        assertNotEquals(item, object, "Backlog items should be equal");
    }

    @Test
    final void testEquals_SameBacklogItemComment_ReturnsTrue() {
        // Given
        BacklogItemComment comment1 = createSampleBacklogItemComment();
        BacklogItemComment comment2 = createSampleBacklogItemComment();

        // When-Then
        assertEquals(comment1, comment2, "Backlog items should be equal");
    }

    @Test
    final void testEquals_DifferentBacklogItemComment_ReturnsFalse() {
        // Given
        BacklogItemComment comment1 = createSampleBacklogItemComment();
        BacklogItemComment comment2 = createSampleBacklogItemComment();

        comment2.setBacklogItemCommentId(2L);

        // When-Then
        assertNotEquals(comment1, comment2, "Backlog items should not be equal");
    }

    @Test
    final void testEquals_NonBacklogItemCommentObject_ReturnsFalse() {
        // Given
        BacklogItemComment comment = createSampleBacklogItemComment();
        Object nonCommentObject = new Object();

        // When-Then
        assertNotEquals(comment, nonCommentObject,
                "Backlog item should not be equal to non-backlog item object");
    }

    @Test
    final void testHashCode_EqualBacklogItemComments_HashCodeMatches() {
        // Given
        BacklogItemComment comment1 = createSampleBacklogItemComment();
        BacklogItemComment comment2 = createSampleBacklogItemComment();

        // When-Then
        assertEquals(comment1.hashCode(), comment2.hashCode(), "Hash codes should match");
    }

    @Test
    final void testHashCode_DifferentBacklogItemComments_HashCodeNotMatches() {
        // Given
        BacklogItemComment comment1 = createSampleBacklogItemComment();
        BacklogItemComment comment2 = createSampleBacklogItemComment();

        comment2.setBacklogItemCommentId(2L);

        // When-Then
        assertNotEquals(comment1.hashCode(), comment2.hashCode(), "Hash codes should not match");
    }

    @Test
    final void testToJson_ReturnsValidJson() {
        // Given
        BacklogItemComment comment = createSampleBacklogItemComment();

        // When
        String json = comment.toJson();

        // Then
        assertNotNull(json, "Json should not be null");
    }

    @Test
    final void testToPrettyJson_ReturnsValidPrettyJson() {
        // Given
        BacklogItemComment comment = createSampleBacklogItemComment();

        // When
        String prettyJson = comment.toPrettyJson();

        // Then
        assertNotNull(prettyJson, "Json should not be null");
    }

    private BacklogItemComment createSampleBacklogItemComment() {
        return BacklogItemComment.builder()
                .backlogItemCommentId(1L)
                .comment("Sample comment")
                .user(new User())
                .backlogItem(new BacklogItem())
                .build();
    }
}
