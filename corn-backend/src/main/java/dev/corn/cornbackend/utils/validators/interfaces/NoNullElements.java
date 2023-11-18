package dev.corn.cornbackend.utils.validators.interfaces;

import dev.corn.cornbackend.utils.validators.NoNullElementsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NoNullElementsValidator.class)
public @interface NoNullElements {
    String message() default "List cannot be null nor contain null elements";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
