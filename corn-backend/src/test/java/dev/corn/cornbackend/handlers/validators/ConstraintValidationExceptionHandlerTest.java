package dev.corn.cornbackend.handlers.validators;

import dev.corn.cornbackend.utils.handlers.data.ConstraintExceptionResponse;
import dev.corn.cornbackend.utils.handlers.interfaces.ConstraintValidationExceptionHandler;
import dev.corn.cornbackend.utils.handlers.validators.ConstraintValidationExceptionHandlerImpl;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import jakarta.validation.metadata.ConstraintDescriptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = ConstraintValidationExceptionHandlerImpl.class)
class ConstraintValidationExceptionHandlerTest {
    @Autowired
    private ConstraintValidationExceptionHandler constraintValidationExceptionHandler;

    @Test
    final void testHandleConstraintViolationException() {
        // given
        ConstraintViolationException exception = new ConstraintViolationException("message", Collections.singleton(new MyConstraintViolation()));

        // when
        ResponseEntity<ConstraintExceptionResponse> response = constraintValidationExceptionHandler
                .handleConstraintViolationException(exception);

        // then
        assertNotNull(response, "Response is null");
    }

    private static class MyConstraintViolation implements ConstraintViolation<Object> {
        @Override
        public String getMessage() {
            return null;
        }

        @Override
        public String getMessageTemplate() {
            return null;
        }

        @Override
        public Object getRootBean() {
            return null;
        }

        @Override
        public Class<Object> getRootBeanClass() {
            return null;
        }

        @Override
        public Object getLeafBean() {
            return null;
        }

        @Override
        public Object[] getExecutableParameters() {
            return new Object[0];
        }

        @Override
        public Object getExecutableReturnValue() {
            return null;
        }

        @Override
        public Path getPropertyPath() {
            return null;
        }

        @Override
        public Object getInvalidValue() {
            return null;
        }

        @Override
        public ConstraintDescriptor<?> getConstraintDescriptor() {
            return null;
        }

        @Override
        public <U> U unwrap(Class<U> type) {
            return null;
        }
    }
}
