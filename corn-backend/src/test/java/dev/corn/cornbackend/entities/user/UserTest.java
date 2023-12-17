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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    final void test_addEmptyListOfAuthorities() {
        // given
        User user = createSampleUser();

        user.setAuthorities(Collections.emptyList());

        // when
        assertTrue(user.getAuthorities().isEmpty(), "Should return empty list of authorities");
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

        // when

        // then
        assertTrue(validateField(
                        user,
                        UserConstants.USER_NAME_FIELD_NAME,
                        UserConstants.USER_NAME_BLANK_MSG),
                "Should return blank name violation");
        assertTrue(validateField(
                        user,
                        UserConstants.USER_SURNAME_FIELD_NAME,
                        UserConstants.USER_SURNAME_BLANK_MSG),
                "Should return blank surname violation");
        assertTrue(validateField(
                        user,
                        UserConstants.USER_USERNAME_FIELD_NAME,
                        UserConstants.USER_USERNAME_BLANK_MSG),
                "Should return blank username violation");
    }

    @Test
    final void test_shouldReturnWrongSizeViolationOnTooLongStringsOnMaxSizeFields() {
        // given
        User user = new User();
        int wrongNameSize = UserConstants.USER_NAME_MAX_SIZE + 1;
        int wrongSurnameSize = UserConstants.USER_SURNAME_MAX_SIZE + 1;
        int wrongUsernameSize = UserConstants.USER_USERNAME_MAX_SIZE + 1;
        user.setName("a".repeat(wrongNameSize));
        user.setSurname("b".repeat(wrongSurnameSize));
        user.setUsername("c".repeat(wrongUsernameSize));

        // when

        // then
        assertTrue(validateField(
                        user,
                        UserConstants.USER_NAME_FIELD_NAME,
                        UserConstants.USER_NAME_WRONG_SIZE_MSG),
                "Should return wrong size name violation");
        assertTrue(validateField(
                        user,
                        UserConstants.USER_SURNAME_FIELD_NAME,
                        UserConstants.USER_SURNAME_WRONG_SIZE_MSG),
                "Should return wrong size surname violation");
        assertTrue(validateField(
                        user,
                        UserConstants.USER_USERNAME_FIELD_NAME,
                        UserConstants.USER_USERNAME_WRONG_SIZE_MSG),
                "Should return wrong size username violation");
    }

    @Test
    final void test_shouldReturnEmptyListOfAuthorities() {
        // given
        User user = createSampleUser();

        // when
        int authoritiesSize = user.getAuthorities().size();

        // then
        assertEquals(0, authoritiesSize, "Should return empty list of authorities");
    }

    @Test
    final void test_shouldReturnNoViolationsOnCorrectUser() {
        // given
        User user = createSampleUser();

        // when
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        // then
        assertEquals(0, violations.size(),
                "Should return no violations on correct user");
    }

    @Test
    final void testReadObjectThrowsException() {
        User user = new User();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try (ObjectOutput objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(user);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

            // Ensure that readObject method throws NotSerializableException
            assertThrows(NotSerializableException.class, objectInputStream::readObject);
        } catch (IOException e) {
            assert true;
        }
    }

    @Test
    final void testWriteObjectThrowsException() {
        // given
        User user = new User();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // when and then

        try (ObjectOutput objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            assertThrows(NotSerializableException.class, () -> objectOutputStream.writeObject(user));
        } catch (IOException e) {
            assert false;
        }
    }

    private User createSampleUser() {
        return User.builder()
                .userId(1L)
                .name("John")
                .surname("Doe")
                .username("johndoe")
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
