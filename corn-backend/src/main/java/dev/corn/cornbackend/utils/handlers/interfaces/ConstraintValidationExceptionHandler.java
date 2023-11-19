package dev.corn.cornbackend.utils.handlers.interfaces;

import dev.corn.cornbackend.utils.handlers.data.ConstraintExceptionResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;

@FunctionalInterface
public interface ConstraintValidationExceptionHandler {
    ResponseEntity<ConstraintExceptionResponse> handleConstraintViolationException(ConstraintViolationException exception);
}
