package dev.corn.cornbackend.utils.validators;

import dev.corn.cornbackend.utils.validators.interfaces.LaterThan;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.Payload;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.lang.annotation.Annotation;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LaterThanValidatorTest {
    @Mock
    private static final ConstraintValidator<LaterThan, Object> validator = new LaterThanValidator();

    private static final String firstDateGetterName = "firstDateGetter";
    private static final String secondDateGetterName = "secondDateGetter";

    @BeforeAll
    static void initializeValidator() {
        validator.initialize(generateConstraintAnnotation());
    }

    private static LaterThan generateConstraintAnnotation() {
        return new MyLaterThan();
    }

    @Test
    final void test_shouldNotValidateIfGivenObjectIsNull() {
        // given
        Object object = null;

        // when

        // then
        assertFalse(validator.isValid(object, null),
                "Should not validate if given object is null");
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

        // then
        assertFalse(validator.isValid(new ExampleObjectNoSecondMethod(), null),
                "Should not validate if second method does not exist on given object");
    }

    @Test
    final void test_shouldNotValidateIfFirstMethodIsPrivateOnGivenObject() {
        // given

        // when

        // then
        assertFalse(validator.isValid(new ExampleObjectFirstMethodPrivate(), null),
                "Should not validate if first method is private on given object");
    }

    @Test
    final void test_shouldNotValidateIfSecondMethodIsPrivateOnGivenObject() {
        // given

        // when

        // then
        assertFalse(validator.isValid(new ExampleObjectSecondMethodPrivate(), null),
                "Should not validate if second method is private on given object");
    }

    @Test
    final void test_shouldNotValidateIfAnyOfTheMethodsNeedsArguments() {
        // given

        // when

        // then
        assertFalse(validator.isValid(new ExampleObjectFirstGetterRequiresArguments(), null),
                "Should not validate if first getter requires arguments");
        assertFalse(validator.isValid(new ExampleObjectSecondGetterRequiresArguments(), null),
                "Should not validate if second getter requires arguments");
    }

    @Test
    final void test_shouldValidateIfAnyOfTheDatesIsNull() {
        // given

        // when

        // then
        assertTrue(validator.isValid(new ExampleObjectReturnFirstNull(), null),
                "Should validate if first date is null");
        assertTrue(validator.isValid(new ExampleObjectReturnSecondNull(), null),
                "Should validate if second date is null");
    }

    @Test
    final void test_shouldNotValidateIfAnyOfTheValidatedFieldsIsNotLocalDate() {
        // given

        // when

        // then
        assertFalse(validator.isValid(new ExampleObjectFirstNotLocalDate(), null),
                "Should not validate if first object is not LocalDate");
        assertFalse(validator.isValid(new ExampleObjectSecondNotLocalDate(), null),
                "Should not validate if second object is not LocalDate");
    }

    @Test
    final void test_shouldNotValidateIfFirstDateIsAfterSecondDate() {
        // given

        // when

        // then
        assertFalse(validator.isValid(new ExampleObjectFirstDateAfterSecond(), null),
                "Should not validate if first date is after the second date");
    }

    @Test
    final void test_shouldValidateIfDatesAreInCorrectOrder() {
        // given

        // when

        // then
        assertTrue(validator.isValid(new ExampleObjectCorrectDates(), null),
                "Should validate if dates are in correct order");
    }

    public static class MyLaterThan implements LaterThan {
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
        public LocalDate secondDateGetter() {
            return null;
        }
    }

    private record ExampleObjectNoSecondMethod() {
        public LocalDate firstDateGetter() {
            return null;
        }
    }

    private record ExampleObjectFirstMethodPrivate() {
        private LocalDate firstDateGetter() {
            return null;
        }

        public LocalDate secondDateGetter() {
            return null;
        }
    }

    private record ExampleObjectSecondMethodPrivate() {
        public LocalDate firstDateGetter() {
            return null;
        }

        private LocalDate secondDateGetter() {
            return null;
        }
    }

    private record ExampleObjectReturnFirstNull() {
        public LocalDate firstDateGetter() {
            return null;
        }

        public LocalDate secondDateGetter() {
            return LocalDate.now();
        }
    }

    private record ExampleObjectReturnSecondNull() {
        public LocalDate firstDateGetter() {
            return LocalDate.now();
        }

        public LocalDate secondDateGetter() {
            return null;
        }
    }

    private record ExampleObjectFirstNotLocalDate() {
        public String firstDateGetter() {
            return "a";
        }

        public LocalDate secondDateGetter() {
            return LocalDate.now();
        }
    }

    private record ExampleObjectSecondNotLocalDate() {
        public LocalDate firstDateGetter() {
            return LocalDate.now();
        }

        public String secondDateGetter() {
            return "a";
        }
    }

    private record ExampleObjectFirstGetterRequiresArguments() {
        public LocalDate firstDateGetter(int i) {
            return LocalDate.now();
        }

        public LocalDate secondDateGetter() {
            return LocalDate.now().plusDays(2L);
        }
    }

    private record ExampleObjectSecondGetterRequiresArguments() {
        public LocalDate firstDateGetter() {
            return LocalDate.now();
        }

        public LocalDate secondDateGetter(int i) {
            return LocalDate.now().plusDays(2L);
        }
    }

    private record ExampleObjectFirstDateAfterSecond() {
        public LocalDate firstDateGetter() {
            return LocalDate.now().plusDays(2L);
        }

        public LocalDate secondDateGetter() {
            return LocalDate.now();
        }
    }

    private record ExampleObjectCorrectDates() {
        public LocalDate firstDateGetter() {
            return LocalDate.now();
        }

        public LocalDate secondDateGetter() {
            return LocalDate.now().plusDays(2L);
        }
    }

}