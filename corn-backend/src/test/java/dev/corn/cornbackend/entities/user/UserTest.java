package dev.corn.cornbackend.entities.user;

import dev.corn.cornbackend.entities.user.constants.UserConstants;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = LocalValidatorFactoryBean.class)
class UserTest {

    @Autowired
    private LocalValidatorFactoryBean validator;

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

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " \n\t\r"})
    final void test_shouldReturnBlankViolationOnGivenNullEmptyOrOnlyWhiteSpaceStringOnNotBlankFields(String string) {
        // given
        User user = new User();
        user.setName(string);
        user.setSurname(string);
        user.setUsername(string);
        user.setPassword(string);
        user.setSalt(string);

        // when

        // then
        assertTrue(validateField(
                user,
                UserConstants.USER_NAME_FIELD_NAME,
                UserConstants.USER_NAME_BLANK_MSG));
        assertTrue(validateField(
                user,
                UserConstants.USER_SURNAME_FIELD_NAME,
                UserConstants.USER_SURNAME_BLANK_MSG));
        assertTrue(validateField(
                user,
                UserConstants.USER_USERNAME_FIELD_NAME,
                UserConstants.USER_USERNAME_BLANK_MSG));
        assertTrue(validateField(
                user,
                UserConstants.USER_PASSWORD_FIELD_NAME,
                UserConstants.USER_PASSWORD_BLANK_MSG));
        assertTrue(validateField(
                user,
                UserConstants.USER_SALT_FIELD_NAME,
                UserConstants.USER_SALT_BLANK_MSG));
    }

    @Test
    final void test_shouldReturnWrongSizeViolationOnTooLongStringsOnMaxSizeFields() {
        // given
        User user = new User();
        user.setName("a".repeat(41));
        user.setSurname("b".repeat(51));
        user.setUsername("c".repeat(51));

        // when

        // then
        assertTrue(validateField(
                user,
                UserConstants.USER_NAME_FIELD_NAME,
                UserConstants.USER_NAME_WRONG_SIZE_MSG));
        assertTrue(validateField(
                user,
                UserConstants.USER_SURNAME_FIELD_NAME,
                UserConstants.USER_SURNAME_WRONG_SIZE_MSG));
        assertTrue(validateField(
                user,
                UserConstants.USER_USERNAME_FIELD_NAME,
                UserConstants.USER_USERNAME_WRONG_SIZE_MSG));
    }

    @Test
    final void test_shouldReturnNoViolationsOnCorrectUser() {
        // given
        User user = createSampleUser();

        // when
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        // then
        assertEquals(0, violations.size());
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

    private boolean validateField(User user, String fieldName, String expectedMessage) {
        Set<ConstraintViolation<User>> violations = validator.validateProperty(
                user,
                fieldName
        );
        return violations.size() == 1 && Objects.equals(violations.iterator().next().getMessage(), expectedMessage);
    }

}
