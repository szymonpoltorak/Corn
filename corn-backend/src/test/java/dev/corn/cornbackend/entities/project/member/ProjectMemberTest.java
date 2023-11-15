package dev.corn.cornbackend.entities.project.member;

import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.user.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProjectMemberTest {
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

    private ProjectMember createSampleProjectMember() {
        return ProjectMember.builder()
                .projectMemberId(1L)
                .backlogItem(new ArrayList<>())
                .project(new Project())
                .user(new User())
                .build();
    }
}
