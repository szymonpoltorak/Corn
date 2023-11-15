package dev.corn.cornbackend.entities.sprint;

import dev.corn.cornbackend.entities.project.Project;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SprintTest {
    @Test
    final void toJson_ReturnsValidJson() {
        // Given
        Sprint sprint = createSampleSprint();

        // When
        String json = sprint.toJson();

        // Then
        assertNotNull(json, "Json should not be null");
    }

    @Test
    final void toPrettyJson_ReturnsValidPrettyJson() {
        // Given
        Sprint sprint = createSampleSprint();

        // When
        String prettyJson = sprint.toPrettyJson();

        // Then
        assertNotNull(prettyJson, "Pretty json should not be null");
    }

    @Test
    final void testEquals_sameSprint() {
        // Given
        Sprint sprint = new Sprint();

        // When-Then
        assertEquals(sprint, sprint,
                "Sprint should be equal to itself");
    }

    @Test
    final void testEquals_differentDescription() {
        // Given
        Sprint sprint = Sprint.builder().sprintDescription("dasda").build();
        Sprint sprint2 = new Sprint();

        // When-Then
        assertNotEquals(sprint, sprint2,
                "Sprint should not be equal to another sprint with different description");
    }

    @Test
    final void testEquals_differentStartDate() {
        // Given
        Sprint sprint = Sprint.builder().sprintStartDate(LocalDate.now()).build();
        Sprint sprint2 = new Sprint();

        // When-Then
        assertNotEquals(sprint, sprint2,
                "Sprint should not be equal to another sprint with different start date");
    }

    @Test
    final void test_toString_notEmptyString() {
        // Given
        Sprint sprint = Sprint.builder().build();

        // When
        String sprintString = sprint.toString();

        // Then
        assertNotEquals("", sprintString, "Sprint string should not be empty");
    }

    @Test
    final void testEquals_SameSprint_ReturnsTrue() {
        // Given
        Sprint sprint1 = createSampleSprint();
        Sprint sprint2 = createSampleSprint();

        // When-Then
        assertEquals(sprint1, sprint2, "Sprints should be equal");
    }

    @Test
    final void testEquals_DifferentSprint_ReturnsFalse() {
        // Given
        Sprint sprint1 = createSampleSprint();
        Sprint sprint2 = createSampleSprint();

        sprint2.setSprintId(2L);

        // When-Then
        assertNotEquals(sprint1, sprint2, "Sprints should not be equal");
    }

    @Test
    final void testEquals_NonSprintObject_ReturnsFalse() {
        // Given
        Sprint sprint = createSampleSprint();
        Object nonSprintObject = new Object();

        // When-Then
        assertNotEquals(sprint, nonSprintObject, "Sprint should not be equal to non sprint object");
    }

    @Test
    final void testHashCode_EqualSprints_HashCodeMatches() {
        // Given
        Sprint sprint1 = createSampleSprint();
        Sprint sprint2 = createSampleSprint();

        // When-Then
        assertEquals(sprint1.hashCode(), sprint2.hashCode(), "Hash code should match");
    }

    @Test
    final void testHashCode_EqualSprints_HashCodeMatches_nulls() {
        // Given
        Sprint sprint1 = Sprint.builder().build();
        Sprint sprint2 = Sprint.builder().build();

        // When-Then
        assertEquals(sprint1.hashCode(), sprint2.hashCode(), "Hash code should match");
    }

    @Test
    final void testHashCode_DifferentSprints_HashCodeNotMatches() {
        // Given
        Sprint sprint1 = createSampleSprint();
        Sprint sprint2 = createSampleSprint();

        sprint2.setSprintId(2L);

        // When-Then
        assertNotEquals(sprint1.hashCode(), sprint2.hashCode(), "Hash code should not match");
    }

    private Sprint createSampleSprint() {
        return Sprint.builder()
                .sprintId(1L)
                .project(new Project())
                .sprintName("Sample Sprint")
                .sprintDescription("Sample description")
                .sprintStartDate(LocalDate.now())
                .sprintEndDate(LocalDate.now().plusDays(7L))
                .build();
    }
}
