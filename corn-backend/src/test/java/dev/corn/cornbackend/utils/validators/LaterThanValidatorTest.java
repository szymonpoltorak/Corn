package dev.corn.cornbackend.utils.validators;

import dev.corn.cornbackend.utils.validators.interfaces.LaterThan;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.Payload;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.print.attribute.standard.JobKOctets;
import java.lang.annotation.Annotation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

class LaterThanValidatorTest {

    @Mock
    private static final ConstraintValidator<LaterThan, Object> validator = new LaterThanValidator();

    @Mock
    private final Object exampleObject = new Object();

    private static final String firstDateGetterName = "firstDateGetter()";
    private static final String secondDateGetterName = "secondDateGetter()";

    @BeforeAll
    static void initializeValidator() {
        validator.initialize(generateConstraintAnnotation());
    }

    @Test
    final void test_shouldNotValidateIfFirstMethodDoesNotExistOnGivenObject() {
        // given

        //when
        try {
            when(exampleObject.getClass().getDeclaredMethod(firstDateGetterName)).thenThrow(NoSuchMethodException.class);
        } catch (Exception e) {

        }

        //then
        assertFalse(validator.isValid(exampleObject, null));
    }

    @Test
    final void test_shouldNotValidateIfSecondMethodDoesNotExistOnGivenObject() {
        // given

        //when
        try {
            when(exampleObject.getClass().getDeclaredMethod(secondDateGetterName)).thenThrow(NoSuchMethodException.class);
        } catch (Exception e) {

        }

        //then
        assertFalse(validator.isValid(exampleObject, null));
    }

    private static LaterThan generateConstraintAnnotation() {
        return new MyLaterThan();
    }

    private static class MyLaterThan implements LaterThan {

        @Override
        public final Class<? extends Annotation> annotationType() {
            return null;
        }

        @Override
        public final String message() {
            return null;
        }

        @Override
        public final Class<?>[] groups() {
            return new Class[0];
        }

        @Override
        public final Class<? extends Payload>[] payload() {
            return new Class[0];
        }

        @Override
        public final String firstDateGetterName() {
            return firstDateGetterName;
        }

        @Override
        public final String secondDateGetterName() {
            return secondDateGetterName;
        }
    }
}