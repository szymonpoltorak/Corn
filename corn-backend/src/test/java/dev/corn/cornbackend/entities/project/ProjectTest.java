package dev.corn.cornbackend.entities.project;

import dev.corn.cornbackend.entities.project.constants.ProjectConstants;
import dev.corn.cornbackend.entities.sprint.Sprint;
import dev.corn.cornbackend.entities.user.User;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = LocalValidatorFactoryBean.class)
class ProjectTest {

    @Autowired
    private LocalValidatorFactoryBean validator;

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

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " \n\t\r"})
    final void test_shouldReturnBlankViolationOnNullEmptyOrOnlyWhitespaceName(String name) {
        // given
        Project project = new Project();
        project.setName(name);

        // when

        // then
        assertTrue(validateField(
                project,
                ProjectConstants.PROJECT_NAME_FIELD_NAME,
                ProjectConstants.PROJECT_NAME_BLANK_MSG));
    }

    @Test
    final void test_shouldReturnSizeViolationOnNameWithMoreThan100Characters() {
        // given
        Project project = new Project();
        project.setName("a".repeat(101));

        // when

        // then
        assertTrue(validateField(
                project,
                ProjectConstants.PROJECT_NAME_FIELD_NAME,
                ProjectConstants.PROJECT_NAME_WRONG_SIZE_MSG));
    }

    @Test
    final void test_shouldReturnNullElementsViolationOnNullSprints() {
        // given
        Project project = new Project();
        project.setSprints(null);

        // when

        // then
        assertTrue(validateField(
                project,
                ProjectConstants.PROJECT_SPRINTS_FIELD_NAME,
                ProjectConstants.PROJECT_SPRINTS_NULL_ELEMENTS_MSG));
    }

    @Test
    final void test_shouldReturnNullElementsViolationOnSprintsContainingNull() {
        // given
        Project project = new Project();
        project.setSprints(Arrays.stream(new Sprint[]{null}).toList());

        // when

        // then
        assertTrue(validateField(
                project,
                ProjectConstants.PROJECT_SPRINTS_FIELD_NAME,
                ProjectConstants.PROJECT_SPRINTS_NULL_ELEMENTS_MSG));
    }

    @Test
    final void test_shouldReturnNullViolationOnNullOwner() {
        // given
        Project project = new Project();
        project.setOwner(null);

        // when

        // then
        assertTrue(validateField(
                project,
                ProjectConstants.PROJECT_OWNER_FIELD_NAME,
                ProjectConstants.PROJECT_OWNER_NULL_MSG));
    }

    @Test
    final void test_shouldReturnNoViolationsOnCorrectProject() {
        // given
        Project project = new Project();
        project.setName("name");
        project.setSprints(new ArrayList<>());
        project.setOwner(new User());

        // when
        Set<ConstraintViolation<Project>> violations = validator.validate(project);

        // then
        assertEquals(0, violations.size());
    }

    private boolean validateField(Project project, String fieldName, String expectedMessage) {
        Set<ConstraintViolation<Project>> violations = validator.validateProperty(
                project,
                fieldName
        );
        return violations.size() == 1 && Objects.equals(violations.iterator().next().getMessage(), expectedMessage);
    }
}
