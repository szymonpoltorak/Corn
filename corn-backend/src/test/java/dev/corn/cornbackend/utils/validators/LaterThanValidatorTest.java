package dev.corn.cornbackend.utils.validators;

import dev.corn.cornbackend.utils.validators.interfaces.LaterThan;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.Payload;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.lang.annotation.Annotation;

import static org.junit.jupiter.api.Assertions.assertFalse;

class LaterThanValidatorTest {
    @Mock
    private static final ConstraintValidator<LaterThan, Object> validator = new LaterThanValidator();

    private static final String firstDateGetterName = "firstDateGetter()";
    private static final String secondDateGetterName = "secondDateGetter()";

    @BeforeAll
    static void initializeValidator() {
        validator.initialize(generateConstraintAnnotation());
    }

    @Test
    final void test_shouldNotValidateIfFirstMethodDoesNotExistOnGivenObject() {
        // given

        // when

        // then
        assertFalse(validator.isValid(new ExampleObjectNoFirstMethod(), null),
                "Should not validate if first method does not exist on given object");
    }

    @Test
    final void test_shouldNotValidateIfSecondMethodDoesNotExistOnGivenObject() {
        // given

        // when

        //then
        assertFalse(validator.isValid(new ExampleObjectNoSecondMethod(), null),
                "Should not validate if second method does not exist on given object");
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

    private record ExampleObjectNoFirstMethod() {
        public String secondDateGetter() {
            return "";
        }
    }

    private record ExampleObjectNoSecondMethod() {
        public String firstDateGetter() {
            return "";
        }
    }
}