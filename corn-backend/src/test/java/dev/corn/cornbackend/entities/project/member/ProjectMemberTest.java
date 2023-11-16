package dev.corn.cornbackend.entities.project.member;

import dev.corn.cornbackend.entities.backlog.comment.BacklogItemComment;
import dev.corn.cornbackend.entities.backlog.item.BacklogItem;
import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.project.member.constants.ProjectMemberConstants;
import dev.corn.cornbackend.entities.user.User;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = LocalValidatorFactoryBean.class)
class ProjectMemberTest {

    @Autowired
    private LocalValidatorFactoryBean validator;

    @Test
    final void testEquals_SameProjectMember_ReturnsTrue() {
        // Given
        ProjectMember member1 = createSampleProjectMember();
        ProjectMember member2 = createSampleProjectMember();

        // When-Then
        assertEquals(member1, member2, "Project members should be equal");
    }

    @Test
    final void testEquals_SameProjectMember() {
        // Given
        ProjectMember member1 = new ProjectMember();

        // When-Then
        assertEquals(member1, member1, "Project members should be equal");
    }

    @Test
    final void test_toString_ReturnsValidString() {
        // Given
        ProjectMember member = createSampleProjectMember();

        // When
        String string = member.toString();

        // Then
        assertNotNull(string, "String should not be null");
    }

    @Test
    final void testEquals_DifferentProject() {
        // Given
        ProjectMember member1 = createSampleProjectMember();
        ProjectMember member2 = createSampleProjectMember();

        member2.setProject(Project.builder().name("dasdas").build());

        // When-Then
        assertNotEquals(member1, member2, "Project members should not be equal");
    }

    @Test
    final void testEquals_DifferentProjectMember_ReturnsFalse() {
        // Given
        ProjectMember member1 = createSampleProjectMember();
        ProjectMember member2 = createSampleProjectMember();

        member2.setProjectMemberId(2L);

        // When-Then
        assertNotEquals(member1, member2, "Project members should not be equal");
    }

    @Test
    final void testEquals_NonProjectMemberObject_ReturnsFalse() {
        // Given
        ProjectMember member = createSampleProjectMember();
        Object nonMemberObject = new Object();

        // When-Then
        assertNotEquals(member, nonMemberObject,
                "Project member should not be equal to non-project member object");
    }

    @Test
    final void testHashCode_EqualProjectMembers_HashCodeMatches() {
        // Given
        ProjectMember member1 = createSampleProjectMember();
        ProjectMember member2 = createSampleProjectMember();

        // When-Then
        assertEquals(member1.hashCode(), member2.hashCode(), "Hash codes should match");
    }

    @Test
    final void testHashCode_DifferentProjectMembers_HashCodeNotMatches() {
        // Given
        ProjectMember member1 = createSampleProjectMember();
        ProjectMember member2 = createSampleProjectMember();

        member2.setProjectMemberId(2L);

        // When-Then
        assertNotEquals(member1.hashCode(), member2.hashCode(), "Hash codes should not match");
    }

    @Test
    final void testToJson_ReturnsValidJson() {
        // Given
        ProjectMember member = createSampleProjectMember();

        // When
        String json = member.toJson();

        // Then
        assertNotNull(json, "Json should not be null");
    }

    @Test
    final void testToPrettyJson_ReturnsValidPrettyJson() {
        // Given
        ProjectMember member = createSampleProjectMember();

        // When
        String prettyJson = member.toPrettyJson();

        // Then
        assertNotNull(prettyJson, "Pretty json should not be null");
    }

    @Test
    final void test_shouldReturnNullElementsViolationOnNullBacklogItems() {
        // given
        ProjectMember projectMember = new ProjectMember();
        projectMember.setBacklogItems(null);

        // when

        // then
        assertTrue(validateField(
                projectMember,
                ProjectMemberConstants.PROJECT_MEMBER_BACKLOG_ITEM_FIELD_NAME,
                ProjectMemberConstants.PROJECT_MEMBER_BACKLOG_ITEM_NULL_ELEMENTS_MSG));
    }

    @Test
    final void test_shouldReturnNullElementsViolationOnBacklogItemsContainingNull() {
        // given
        ProjectMember projectMember = new ProjectMember();
        projectMember.setBacklogItems(Arrays.stream(new BacklogItem[]{null}).toList());

        // when

        // then
        assertTrue(validateField(
                projectMember,
                ProjectMemberConstants.PROJECT_MEMBER_BACKLOG_ITEM_FIELD_NAME,
                ProjectMemberConstants.PROJECT_MEMBER_BACKLOG_ITEM_NULL_ELEMENTS_MSG));
    }

    @Test
    final void test_shouldReturnNullElementViolationOnNullElementOnNotNullFields() {
        // given
        ProjectMember projectMember = new ProjectMember();
        projectMember.setProject(null);
        projectMember.setUser(null);

        // when

        // then
        assertTrue(validateField(
                projectMember,
                ProjectMemberConstants.PROJECT_MEMBER_PROJECT_FIELD_NAME,
                ProjectMemberConstants.PROJECT_MEMBER_PROJECT_NULL_MSG));
        assertTrue(validateField(
                projectMember,
                ProjectMemberConstants.PROJECT_MEMBER_USER_FIELD_NAME,
                ProjectMemberConstants.PROJECT_MEMBER_USER_NULL_MSG));
    }

    @Test
    final void test_shouldReturnNoViolationsOnCorrectProjectMember() {
        // given
        ProjectMember projectMember = createSampleProjectMember();

        // when
        Set<ConstraintViolation<ProjectMember>> violations = validator.validate(projectMember);

        // then
        assertEquals(0, violations.size());
    }

    private ProjectMember createSampleProjectMember() {
        return ProjectMember.builder()
                .projectMemberId(1L)
                .backlogItems(new ArrayList<>())
                .project(new Project())
                .user(new User())
                .build();
    }

    private boolean validateField(ProjectMember projectMember, String fieldName, String expectedMessage) {
        Set<ConstraintViolation<ProjectMember>> violations = validator.validateProperty(
                projectMember,
                fieldName
        );
        return violations.size() == 1 && Objects.equals(violations.iterator().next().getMessage(), expectedMessage);
    }
}
