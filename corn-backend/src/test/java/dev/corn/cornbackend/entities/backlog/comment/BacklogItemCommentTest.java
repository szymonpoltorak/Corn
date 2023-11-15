package dev.corn.cornbackend.entities.backlog.comment;

import dev.corn.cornbackend.entities.backlog.item.BacklogItem;
import dev.corn.cornbackend.entities.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BacklogItemCommentTest {
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
