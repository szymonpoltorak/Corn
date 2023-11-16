package dev.corn.cornbackend.entities.backlog.item;

import dev.corn.cornbackend.entities.backlog.comment.BacklogItemComment;
import dev.corn.cornbackend.entities.backlog.item.constants.BacklogItemConstants;
import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.project.member.ProjectMember;
import dev.corn.cornbackend.entities.sprint.Sprint;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = LocalValidatorFactoryBean.class)
class BacklogItemTest {
    @Autowired
    private LocalValidatorFactoryBean validator;

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

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " \n\t\r"})
    final void test_shouldReturnBlankViolationOnNullEmptyOrOnlyWhitespaceStringOnNotBlankFields(String string) {
        // given
        BacklogItem backlogItem = new BacklogItem();
        backlogItem.setTitle(string);
        backlogItem.setDescription(string);

        // when

        // then
        assertTrue(validateField(
                backlogItem,
                BacklogItemConstants.BACKLOG_ITEM_TITLE_FIELD_NAME,
                BacklogItemConstants.BACKLOG_ITEM_TITLE_BLANK_MSG));
        assertTrue(validateField(
                backlogItem,
                BacklogItemConstants.BACKLOG_ITEM_DESCRIPTION_FIELD_NAME,
                BacklogItemConstants.BACKLOG_ITEM_DESCRIPTION_BLANK_MSG));
    }

    @Test
    final void test_shouldReturnWrongSizeViolationOnIncorrectSizeStringsOnMaxSizeFields() {
        // given
        BacklogItem backlogItem = new BacklogItem();
        backlogItem.setTitle("a".repeat(101));
        backlogItem.setDescription("b".repeat(501));

        // when

        // then
        assertTrue(validateField(
                backlogItem,
                BacklogItemConstants.BACKLOG_ITEM_TITLE_FIELD_NAME,
                BacklogItemConstants.BACKLOG_ITEM_TITLE_WRONG_SIZE_MSG));
        assertTrue(validateField(
                backlogItem,
                BacklogItemConstants.BACKLOG_ITEM_DESCRIPTION_FIELD_NAME,
                BacklogItemConstants.BACKLOG_ITEM_DESCRIPTION_WRONG_SIZE_MSG));
    }

    @Test
    final void test_shouldReturnNullElementViolationOnNullElementOnNotNullFields() {
        // given
        BacklogItem backlogItem = new BacklogItem();
        backlogItem.setStatus(null);
        backlogItem.setAssignee(null);
        backlogItem.setSprint(null);
        backlogItem.setProject(null);

        // when

        // then
        assertTrue(validateField(
                backlogItem,
                BacklogItemConstants.BACKLOG_ITEM_STATUS_FIELD_NAME,
                BacklogItemConstants.BACKLOG_ITEM_STATUS_NULL_MSG));
        assertTrue(validateField(
                backlogItem,
                BacklogItemConstants.BACKLOG_ITEM_ASSIGNEE_FIELD_NAME,
                BacklogItemConstants.BACKLOG_ITEM_ASSIGNEE_NULL_MSG));
        assertTrue(validateField(
                backlogItem,
                BacklogItemConstants.BACKLOG_ITEM_SPRINT_FIELD_NAME,
                BacklogItemConstants.BACKLOG_ITEM_SPRINT_NULL_MSG));
        assertTrue(validateField(
                backlogItem,
                BacklogItemConstants.BACKLOG_ITEM_PROJECT_FIELD_NAME,
                BacklogItemConstants.BACKLOG_ITEM_PROJECT_NULL_MSG));
    }

    @Test
    final void test_shouldReturnNullElementsViolationOnNullComments() {
        BacklogItem backlogItem = new BacklogItem();
        backlogItem.setComments(null);

        // when

        // then
        assertTrue(validateField(
                backlogItem,
                BacklogItemConstants.BACKLOG_ITEM_COMMENTS_FIELD_NAME,
                BacklogItemConstants.BACKLOG_ITEM_COMMENTS_NULL_ELEMENTS_MSG));
    }

    @Test
    final void test_shouldReturnNullElementsViolationOnCommentsContainingNullComment() {
        BacklogItem backlogItem = new BacklogItem();
        List<BacklogItemComment> list = Arrays.stream(new BacklogItemComment[]{null}).toList();
        backlogItem.setComments(list);

        // when

        // then
        assertTrue(validateField(
                backlogItem,
                BacklogItemConstants.BACKLOG_ITEM_COMMENTS_FIELD_NAME,
                BacklogItemConstants.BACKLOG_ITEM_COMMENTS_NULL_ELEMENTS_MSG));
    }

    @Test
    final void test_shouldReturnNoViolationsOnCorrectBacklogItem() {
        // given
        BacklogItem backlogItem = createSampleBacklogItem();

        // when
        Set<ConstraintViolation<BacklogItem>> violations = validator.validate(backlogItem);

        // then
        assertEquals(0, violations.size());
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

    private boolean validateField(BacklogItem backlogItem, String fieldName, String expectedMessage) {
        Set<ConstraintViolation<BacklogItem>> violations = validator.validateProperty(
                backlogItem,
                fieldName
        );
        return violations.size() == 1 && Objects.equals(violations.iterator().next().getMessage(), expectedMessage);
    }

}
