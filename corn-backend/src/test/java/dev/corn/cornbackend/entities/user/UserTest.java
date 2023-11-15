package dev.corn.cornbackend.entities.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserTest {
    @Test
    final void testEquals_SameUser_ReturnsTrue() {
        // Given
        User user1 = createSampleUser();
        User user2 = createSampleUser();

        // When-Then
        assertEquals(user1, user2, "Users should be equal");
    }

    @Test
    final void testEquals_SameUser() {
        // Given
        User user1 = new User();

        // When-Then
        assertEquals(user1, user1, "Users should be equal");
    }

    @Test
    final void testEquals_DifferentName() {
        // Given
        User user1 = createSampleUser();
        User user2 = createSampleUser();

        user2.setName("John2");

        // When-Then
        assertNotEquals(user1, user2, "Users should not be equal");
    }

    @Test
    final void testEquals_DifferentUser_ReturnsFalse() {
        // Given
        User user1 = createSampleUser();
        User user2 = createSampleUser();

        user2.setUserId(2L);

        // When-Then
        assertNotEquals(user1, user2, "Users should not be equal");
    }

    @Test
    final void testEquals_NonUserObject_ReturnsFalse() {
        // Given
        User user = createSampleUser();
        Object nonUserObject = new Object();

        // When-Then
        assertNotEquals(user, nonUserObject, "User should not be equal to non-user object");
    }

    @Test
    final void testHashCode_EqualUsers_HashCodeMatches() {
        // Given
        User user1 = createSampleUser();
        User user2 = createSampleUser();

        // When-Then
        assertEquals(user1.hashCode(), user2.hashCode(), "Hash codes should match");
    }

    @Test
    final void testHashCode_DifferentUsers_HashCodeNotMatches() {
        // Given
        User user1 = createSampleUser();
        User user2 = createSampleUser();

        user2.setUserId(2L);

        // When-Then
        assertNotEquals(user1.hashCode(), user2.hashCode(), "Hash codes should not match");
    }

    @Test
    final void testToJson_ReturnsValidJson() {
        // Given
        User user = createSampleUser();

        // When
        String json = user.toJson();

        // Then
        assertNotNull(json, "Json should not be null");
    }

    @Test
    final void testToPrettyJson_ReturnsValidPrettyJson() {
        // Given
        User user = createSampleUser();

        // When
        String prettyJson = user.toPrettyJson();

        // Then
        assertNotNull(prettyJson, "Pretty json should not be null");
    }

    private User createSampleUser() {
        return User.builder()
                .userId(1L)
                .name("John")
                .surname("Doe")
                .username("johndoe")
                .password("password")
                .salt("salt")
                .build();
    }

}
