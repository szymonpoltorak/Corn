package dev.corn.cornbackend.entities.sprint;

import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.sprint.constants.SprintConstants;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = LocalValidatorFactoryBean.class)
class SprintTest {

    @Autowired
    private LocalValidatorFactoryBean validator;

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

    @Test
    final void test_shouldReturnNullElementViolationOnNullElementOnNotNullFields() {
        // given
        Sprint sprint = new Sprint();
        sprint.setProject(null);
        sprint.setSprintStartDate(null);
        sprint.setSprintEndDate(null);

        // when

        // then
        assertTrue(validateField(
                        sprint,
                        SprintConstants.SPRINT_PROJECT_FIELD_NAME,
                        SprintConstants.SPRINT_PROJECT_NULL_MSG),
                "Should return null project violation");
        assertTrue(validateField(
                        sprint,
                        SprintConstants.SPRINT_START_DATE_FIELD_NAME,
                        SprintConstants.SPRINT_START_DATE_NULL_MSG),
                "Should return null sprint start date violation");
        assertTrue(validateField(
                        sprint,
                        SprintConstants.SPRINT_END_DATE_FIELD_NAME,
                        SprintConstants.SPRINT_END_DATE_NULL_MSG),
                "Should return null sprint end date violation");
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " \n\t\r"})
    final void test_shouldReturnBlankViolationOnNullEmptyOrOnlyWhitespaceStringOnNotBlankFields(String string) {
        // given
        Sprint sprint = new Sprint();
        sprint.setSprintName(string);
        sprint.setSprintDescription(string);

        // when

        // then
        assertTrue(validateField(
                        sprint,
                        SprintConstants.SPRINT_NAME_FIELD_NAME,
                        SprintConstants.SPRINT_NAME_BLANK_MSG),
                "Should return blank name violation");
        assertTrue(validateField(
                        sprint,
                        SprintConstants.SPRINT_DESCRIPTION_FIELD_NAME,
                        SprintConstants.SPRINT_DESCRIPTION_BLANK_MSG),
                "Should return blank description violation");
    }

    @Test
    final void test_shouldReturnWrongSizeViolationOnTooLongStringOnMaxSizeFields() {
        // given
        Sprint sprint = new Sprint();
        int wrongNameSize = SprintConstants.SPRINT_NAME_MAX_SIZE + 1;
        int wrongDescriptionSize = SprintConstants.SPRINT_DESCRIPTION_MAX_SIZE + 1;
        sprint.setSprintName("a".repeat(wrongNameSize));
        sprint.setSprintDescription("b".repeat(wrongDescriptionSize));

        // when

        // then
        assertTrue(validateField(
                        sprint,
                        SprintConstants.SPRINT_NAME_FIELD_NAME,
                        SprintConstants.SPRINT_NAME_WRONG_SIZE_MSG),
                "Should return wrong size name violation");
        assertTrue(validateField(
                        sprint,
                        SprintConstants.SPRINT_DESCRIPTION_FIELD_NAME,
                        SprintConstants.SPRINT_DESCRIPTION_WRONG_SIZE_MSG),
                "Should return wrong size description violation");
    }

    @Test
    final void test_shouldReturnFutureOrPresentViolationOnPastStartDate() {
        // given
        Sprint sprint = new Sprint();
        sprint.setSprintStartDate(LocalDate.now().minusDays(2L));

        // when

        // then
        assertTrue(validateField(
                        sprint,
                        SprintConstants.SPRINT_START_DATE_FIELD_NAME,
                        SprintConstants.SPRINT_START_DATE_FUTURE_OR_PRESENT_MSG),
                "Should return future or present sprint start date violation");
    }


    @Test
    final void test_shouldReturnFutureViolationOnPastEndDate() {
        // given
        Sprint sprint = new Sprint();
        sprint.setSprintEndDate(LocalDate.now().minusDays(2L));

        // when

        // then
        assertTrue(validateField(
                        sprint,
                        SprintConstants.SPRINT_END_DATE_FIELD_NAME,
                        SprintConstants.SPRINT_END_DATE_FUTURE_MSG),
                "Should return future sprint end date violation");
    }

    @Test
    final void test_shouldReturnFutureViolationOnPresentEndDate() {
        // given
        Sprint sprint = new Sprint();
        sprint.setSprintEndDate(LocalDate.now());

        // when

        // then
        assertTrue(validateField(
                        sprint,
                        SprintConstants.SPRINT_END_DATE_FIELD_NAME,
                        SprintConstants.SPRINT_END_DATE_FUTURE_MSG),
                "Should return future sprint end date violation");
    }

    @Test
    final void test_shouldReturnLaterThanViolationOnStartDateLaterThanEndDate() {
        // given
        Sprint sprint = createSampleSprint();
        sprint.setSprintStartDate(LocalDate.now().plusDays(5L));
        sprint.setSprintEndDate(LocalDate.now().plusDays(2L));

        // when
        Set<ConstraintViolation<Sprint>> violations = validator.validate(sprint);

        // then
        String expectedMessage = SprintConstants.SPRINT_LATER_THAN_MSG;

        assertEquals(1, violations.size(),
                "Should return only one violation");
        assertEquals(expectedMessage, violations.iterator().next().getMessage(),
                "Should return later than violation");
    }

    @Test
    final void test_shouldReturnNoViolationOnCorrectSprint() {
        // given
        Sprint sprint = createSampleSprint();

        // when
        Set<ConstraintViolation<Sprint>> violations = validator.validate(sprint);

        // then
        assertEquals(0, violations.size(),
                "Should return no violations");
    }

    @Test
    void givenSprintWithStartDateInPast_whenIsStartBeforeFutureDate_thenReturnsTrue() {
        // Given
        Sprint sprint = Sprint.builder()
                .sprintStartDate(LocalDate.now().minusDays(1))
                .build();

        // When
        boolean result = sprint.isStartBefore(LocalDate.now().plusDays(1));

        // Then
        assertTrue(result);
    }

    @Test
    void givenSprintWithStartDateInFuture_whenIsStartBeforePastDate_thenReturnsFalse() {
        // Given
        Sprint sprint = Sprint.builder()
                .sprintStartDate(LocalDate.now().plusDays(1))
                .build();

        // When
        boolean result = sprint.isStartBefore(LocalDate.now().minusDays(1));

        // Then
        assertFalse(result);
    }

    @Test
    void givenSprintWithStartDateInPast_whenIsStartAfterFutureDate_thenReturnsFalse() {
        // Given
        Sprint sprint = Sprint.builder()
                .sprintStartDate(LocalDate.now().minusDays(1))
                .build();

        // When
        boolean result = sprint.isStartAfter(LocalDate.now().plusDays(1));

        // Then
        assertFalse(result);
    }

    @Test
    void givenSprintWithStartDateInFuture_whenIsStartAfterPastDate_thenReturnsTrue() {
        // Given
        Sprint sprint = Sprint.builder()
                .sprintStartDate(LocalDate.now().plusDays(1))
                .build();

        // When
        boolean result = sprint.isStartAfter(LocalDate.now().minusDays(1));

        // Then
        assertTrue(result);
    }


    @Test
    void givenSprintWithEndDateInPast_whenIsEndAfterFutureDate_thenReturnsTrue() {
        // Given
        Sprint sprint = Sprint.builder()
                .sprintEndDate(LocalDate.now().minusDays(1))
                .build();

        // When
        boolean result = sprint.isEndAfter(LocalDate.now().plusDays(1));

        // Then
        assertFalse(result);
    }

    @Test
    void givenSprintWithEndDateInFuture_whenIsEndAfterPastDate_thenReturnsFalse() {
        // Given
        Sprint sprint = Sprint.builder()
                .sprintEndDate(LocalDate.now().plusDays(1))
                .build();

        // When
        boolean result = sprint.isEndAfter(LocalDate.now().minusDays(1));

        // Then
        assertTrue(result);
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

    private boolean validateField(Sprint sprint, String fieldName, String expectedMessage) {
        Set<ConstraintViolation<Sprint>> violations = validator.validateProperty(
                sprint,
                fieldName
        );
        return violations.size() == 1 && Objects.equals(violations.iterator().next().getMessage(), expectedMessage);
    }
}
