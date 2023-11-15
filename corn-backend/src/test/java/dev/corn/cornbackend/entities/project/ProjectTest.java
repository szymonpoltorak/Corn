package dev.corn.cornbackend.entities.project;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProjectTest {
    @Test
    final void testEquals_SameProject_ReturnsTrue() {
        // Given
        Project project1 = new Project();

        project1.setProjectId(0L);

        // When-Then
        assertEquals(project1, project1, "Projects should be equal");
    }

    @Test
    final void testEquals_not_instance_of_project() {
        // Given
        Project project1 = new Project();
        Object object = new Object();

        // When-Then
        assertNotEquals(project1, object, "Projects should be equal");
    }

    @Test
    final void test_toJson_returnsValidJson() {
        // Given
        Project project = new Project();

        // When
        String json = project.toJson();

        // Then
        assertNotNull(json, "Json should not be null");
    }

    @Test
    final void test_toPrettyJson_returnsValidJson() {
        // Given
        Project project = new Project();

        // When
        String json = project.toPrettyJson();

        // Then
        assertNotNull(json, "Json should not be null");
    }
}
