package dev.corn.cornbackend.utils.handlers.validators;

import dev.corn.cornbackend.utils.handlers.data.ConstraintExceptionMessageWithValue;
import dev.corn.cornbackend.utils.handlers.data.ConstraintExceptionResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Slf4j
@ControllerAdvice
public class ConstraintValidationExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<ConstraintExceptionResponse> handleConstraintViolationException(ConstraintViolationException exception) {
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();

        String validationErrorClassName = violations.iterator().next().getLeafBean().getClass().getSimpleName();

        List<ConstraintExceptionMessageWithValue> list = violations
                .stream()
                .map(violation -> new ConstraintExceptionMessageWithValue(
                        violation.getMessage(),
                        violation.getInvalidValue() != null ? violation.getInvalidValue().toString() : null))
                .toList();


        LocalDate timeStamp = LocalDate.now();

        return new ResponseEntity<>(ConstraintExceptionResponse
                .builder()
                .validationErrorClassName(validationErrorClassName)
                .errorList(list)
                .timeStamp(timeStamp)
                .build(), HttpStatus.BAD_REQUEST);
    }
}
