package dev.corn.cornbackend.entities.backlog.item;

import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.project.member.ProjectMember;
import dev.corn.cornbackend.entities.sprint.Sprint;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BacklogItemTest {
    @Test
    final void testEquals_SameBacklogItem_ReturnsTrue() {
        // Given
        BacklogItem item1 = createSampleBacklogItem();
        BacklogItem item2 = createSampleBacklogItem();

        // When-Then
        assertEquals(item1, item2, "Backlog items should be equal");
    }

    @Test
    final void testEquals_SameBacklogItem() {
        // Given
        BacklogItem item1 = new BacklogItem();

        // When-Then
        assertEquals(item1, item1, "Backlog items should be equal");
    }

    @Test
    final void test_toString_ReturnsValidString() {
        // Given
        BacklogItem item = createSampleBacklogItem();

        // When
        String string = item.toString();

        // Then
        assertNotNull(string, "String should not be null");
    }

    @Test
    final void testEquals_DifferentDescription() {
        // Given
        BacklogItem item1 = createSampleBacklogItem();
        BacklogItem item2 = createSampleBacklogItem();

        item2.setDescription("dasdas");

        // When-Then
        assertNotEquals(item1, item2, "Backlog items should not be equal");
    }

    @Test
    final void testEquals_DifferentStatus() {
        // Given
        BacklogItem item1 = createSampleBacklogItem();
        BacklogItem item2 = createSampleBacklogItem();

        item2.setStatus(ItemStatus.IN_PROGRESS);

        // When-Then
        assertNotEquals(item1, item2, "Backlog items should not be equal");
    }

    @Test
    final void testEquals_DifferentSprint() {
        // Given
        BacklogItem item1 = createSampleBacklogItem();
        BacklogItem item2 = createSampleBacklogItem();

        item2.setSprint(Sprint.builder().sprintName("dasdas").build());

        // When-Then
        assertNotEquals(item1, item2, "Backlog items should not be equal");
    }

    @Test
    final void testEquals_DifferentBacklogItem_ReturnsFalse() {
        // Given
        BacklogItem item1 = createSampleBacklogItem();
        BacklogItem item2 = createSampleBacklogItem();

        item2.setBacklogItemId(2L);

        // When-Then
        assertNotEquals(item1, item2, "Backlog items should not be equal");
    }

    @Test
    final void testEquals_NonBacklogItemObject_ReturnsFalse() {
        // Given
        BacklogItem item = createSampleBacklogItem();
        Object nonItemObject = new Object();

        // When-Then
        assertNotEquals(item, nonItemObject, "Backlog item should not be equal to non-item object");
    }

    @Test
    final void testHashCode_EqualBacklogItems_HashCodeMatches() {
        // Given
        BacklogItem item1 = createSampleBacklogItem();
        BacklogItem item2 = createSampleBacklogItem();

        // When-Then
        assertEquals(item1.hashCode(), item2.hashCode(), "Hash codes should match");
    }

    @Test
    final void testHashCode_DifferentBacklogItems_HashCodeNotMatches() {
        // Given
        BacklogItem item1 = createSampleBacklogItem();
        BacklogItem item2 = createSampleBacklogItem();

        item2.setBacklogItemId(2L);

        // When-Then
        assertNotEquals(item1.hashCode(), item2.hashCode(), "Hash codes should not match");
    }

    @Test
    final void testToJson_ReturnsValidJson() {
        // Given
        BacklogItem item = createSampleBacklogItem();

        // When
        String json = item.toJson();

        // Then
        assertNotNull(json, "Json should not be null");
    }

    @Test
    final void testToPrettyJson_ReturnsValidPrettyJson() {
        // Given
        BacklogItem item = createSampleBacklogItem();

        // When
        String prettyJson = item.toPrettyJson();

        // Then
        assertNotNull(prettyJson, "Pretty json should not be null");
    }

    private BacklogItem createSampleBacklogItem() {
        return BacklogItem.builder()
                .backlogItemId(1L)
                .title("Sample Title")
                .description("Sample description")
                .status(ItemStatus.TODO)
                .comments(new ArrayList<>())
                .assignee(new ProjectMember())
                .sprint(new Sprint())
                .project(new Project())
                .build();
    }
}
