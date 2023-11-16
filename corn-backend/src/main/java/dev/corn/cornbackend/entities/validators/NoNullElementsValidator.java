package dev.corn.cornbackend.entities.validators;

import dev.corn.cornbackend.entities.validators.interfaces.NoNullElements;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.Objects;

public class NoNullElementsValidator implements ConstraintValidator<NoNullElements, List<?>> {
    @Override
    public void initialize(NoNullElements constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<?> value, ConstraintValidatorContext context) {
        return value != null && value.stream().noneMatch(Objects::isNull);
    }
}
