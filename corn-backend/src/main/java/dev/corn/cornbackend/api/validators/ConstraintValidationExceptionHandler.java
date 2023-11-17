package dev.corn.cornbackend.api.validators;

import dev.corn.cornbackend.api.data.ConstraintExceptionResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;
import java.util.Set;

@Slf4j
@ControllerAdvice
public class ConstraintValidationExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<ConstraintExceptionResponse> handleConstraintViolationException(ConstraintViolationException exception) {
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();

        ConstraintViolation<?> violation = violations.iterator().next();

        String validationErrorClassName = violation.getLeafBean().getClass().getSimpleName();
        String errorMessage = violation.getMessage();

        Object invalidValueObject = violation.getInvalidValue();

        String invalidValue;
        if(invalidValueObject == null) {
            invalidValue = null;
        } else {
            invalidValue = invalidValueObject.toString();
        }

        LocalDate timeStamp = LocalDate.now();

        return new ResponseEntity<>(ConstraintExceptionResponse
                .builder()
                .validationErrorClassName(validationErrorClassName)
                .errorMessage(errorMessage)
                .invalidValue(invalidValue)
                .timeStamp(timeStamp)
                .build(), HttpStatus.BAD_REQUEST);

    }
}
