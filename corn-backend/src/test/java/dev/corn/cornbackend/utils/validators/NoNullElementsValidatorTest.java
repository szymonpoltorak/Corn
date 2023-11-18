package dev.corn.cornbackend.utils.validators;

import dev.corn.cornbackend.utils.validators.interfaces.NoNullElements;
import jakarta.validation.ConstraintValidator;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NoNullElementsValidatorTest {

    private final ConstraintValidator<NoNullElements, List<?>> validator = new NoNullElementsValidator();

    @Test
    final void test_validatorShouldNotValidateANullList() {
        // given
        List<Object> list = null;

        // when
        boolean result = validator.isValid(list, null);

        // then
        assertFalse(result);
    }

    @Test
    final void test_validatorShouldNotValidateAListContainingNullValue() {
        // given
        List<Object> list = Arrays.stream(new Object[]{"a", null, "b"}).toList();

        // when
        boolean result = validator.isValid(list, null);

        // then
        assertFalse(result);
    }

    @Test
    final void test_validatorShouldValidateACorrectList() {
        // given
        List<Object> list = Arrays.stream(new Object[]{"a", "b"}).toList();

        // when
        boolean result = validator.isValid(list, null);

        // then
        assertTrue(result);
    }

}