package dev.corn.cornbackend.utils.handlers.validators;

import dev.corn.cornbackend.utils.handlers.data.ConstraintExceptionMessageWithValue;
import dev.corn.cornbackend.utils.handlers.data.ConstraintExceptionResponse;
import dev.corn.cornbackend.utils.handlers.interfaces.ConstraintValidationExceptionHandler;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Slf4j
@ControllerAdvice
public class ConstraintValidationExceptionHandlerImpl implements ConstraintValidationExceptionHandler {

    @Override
    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<ConstraintExceptionResponse> handleConstraintViolationException(ConstraintViolationException exception) {
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();

        log.error("ConstraintViolationException: {}", violations);

        String validationErrorClassName = getValidationErrorClassName(violations);

        List<ConstraintExceptionMessageWithValue> list = getConstraintViolationMessages(violations);

        return new ResponseEntity<>(buildResponse(validationErrorClassName, list), HttpStatus.BAD_REQUEST);
    }

    private String getValidationErrorClassName(Collection<ConstraintViolation<?>> violations) {
        return violations.iterator().next().getClass().getSimpleName();
    }

    private List<ConstraintExceptionMessageWithValue> getConstraintViolationMessages(Collection<ConstraintViolation<?>> violations) {
        return violations
                .stream()
                .map(violation -> ConstraintExceptionMessageWithValue
                        .builder()
                        .errorMessage(violation.getMessage())
                        .invalidValue(violation.getInvalidValue() != null ? violation.getInvalidValue().toString() : null)
                        .build())
                .toList();
    }

    private ConstraintExceptionResponse buildResponse(String validationErrorClassName,
                                                      List<ConstraintExceptionMessageWithValue> list) {
        return ConstraintExceptionResponse
                .builder()
                .validationErrorClassName(validationErrorClassName)
                .errorList(list)
                .timeStamp(LocalDate.now())
                .build();
    }
}
