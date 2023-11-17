package dev.corn.cornbackend.entities.validators.interfaces;

import dev.corn.cornbackend.entities.validators.LaterThanValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LaterThanValidator.class)
public @interface LaterThan {
    String message() default "Dates are not in the correct order";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String firstDateGetterName();

    String secondDateGetterName();
}
