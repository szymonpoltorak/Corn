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

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = LocalValidatorFactoryBean.class)
class BacklogItemCommentTest {
    private final String ONLY_ONE_VIOLATION = "Validator should return only 1 violation";

    @Autowired
    private LocalValidatorFactoryBean validator;

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

        item2.setBacklogItemCommentId(2L);

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
    final void testHashCode_DifferentBacklogItemComments_HashCodeMatches() {
        // Given
        BacklogItemComment comment1 = createSampleBacklogItemComment();
        BacklogItemComment comment2 = createSampleBacklogItemComment();

        comment2.setBacklogItemCommentId(2L);

        // When-Then
        assertEquals(comment1.hashCode(), comment2.hashCode(), "Hash codes should match");
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

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " \n\t\r"})
    final void test_shouldReturnBlankCommentViolationOnNullEmptyOrOnlyWhiteSpaceComment(String comment) {
        // given
        BacklogItemComment backlogItemComment = new BacklogItemComment();
        backlogItemComment.setComment(comment);

        // when

        // then
        assertTrue(validateField(
                        backlogItemComment,
                        BacklogItemCommentConstants.BACKLOG_ITEM_COMMENT_COMMENT_FIELD_NAME,
                        BacklogItemCommentConstants.BACKLOG_ITEM_COMMENT_COMMENT_BLANK_MSG),
                "Should return blank comment violation");
    }

    @Test
    final void test_shouldReturnSizeViolationOnCommentWithMoreThan500Characters() {
        // given
        BacklogItemComment backlogItemComment = new BacklogItemComment();
        int wrongSize = BacklogItemCommentConstants.BACKLOG_ITEM_COMMENT_MAX_SIZE + 1;
        backlogItemComment.setComment("a".repeat(wrongSize));

        // when

        // then
        assertTrue(validateField(
                        backlogItemComment,
                        BacklogItemCommentConstants.BACKLOG_ITEM_COMMENT_COMMENT_FIELD_NAME,
                        BacklogItemCommentConstants.BACKLOG_ITEM_COMMENT_COMMENT_WRONG_SIZE_MSG),
                "Should return wrong size comment violation");
    }

    @Test
    final void test_shouldReturnNullElementViolationOnNullElementOnNotNullFields() {
        // given
        BacklogItemComment backlogItemComment = new BacklogItemComment();
        backlogItemComment.setUser(null);
        backlogItemComment.setBacklogItem(null);
        backlogItemComment.setLastEditTime(null);
        backlogItemComment.setCommentDate(null);

        // when

        // then
        assertTrue(validateField(
                        backlogItemComment,
                        BacklogItemCommentConstants.BACKLOG_ITEM_COMMENT_USER_FIELD_NAME,
                        BacklogItemCommentConstants.BACKLOG_ITEM_COMMENT_USER_NULL_MSG),
                "Should return null user violation");
        assertTrue(validateField(
                        backlogItemComment,
                        BacklogItemCommentConstants.BACKLOG_ITEM_COMMENT_BACKLOG_ITEM_FIELD_NAME,
                        BacklogItemCommentConstants.BACKLOG_ITEM_COMMENT_BACKLOG_ITEM_NULL_MSG),
                "Should return null backlog item violation");
        assertTrue(validateField(
                backlogItemComment,
                BacklogItemCommentConstants.BACKLOG_ITEM_COMMENT_COMMENT_TIME_FIELD_NAME,
                BacklogItemCommentConstants.BACKLOG_ITEM_COMMENT_COMMENT_TIME_NULL_MSG),
                "Should return null comment time violation");
        assertTrue(validateField(
                backlogItemComment,
                BacklogItemCommentConstants.BACKLOG_ITEM_COMMENT_LAST_EDIT_TIME_FIELD_NAME,
                BacklogItemCommentConstants.BACKLOG_ITEM_COMMENT_LAST_EDIT_TIME_NULL_MSG),
                "Should return null last edit time violation");
    }

    @Test
    final void test_shouldReturnNoViolationsOnCorrectBacklogItemComment() {
        // given
        BacklogItemComment backlogItemComment = createSampleBacklogItemComment();

        // when
        Set<ConstraintViolation<BacklogItemComment>> violations = validator.validate(backlogItemComment);

        // then
        assertEquals(0, violations.size(),
                "Should return no violations on correct backlog item comment");
    }

    private BacklogItemComment createSampleBacklogItemComment() {
        return BacklogItemComment.builder()
                .backlogItemCommentId(1L)
                .comment("Sample comment")
                .user(new User())
                .backlogItem(new BacklogItem())
                .lastEditTime(LocalDateTime.now())
                .commentDate(LocalDateTime.now())
                .build();
    }

    private boolean validateField(BacklogItemComment backlogItemComment, String fieldName, String expectedMessage) {
        Set<ConstraintViolation<BacklogItemComment>> violations = validator.validateProperty(
                backlogItemComment,
                fieldName
        );
        return violations.size() == 1 && Objects.equals(violations.iterator().next().getMessage(), expectedMessage);
    }
}
