package dev.corn.cornbackend.utils.validators;

import dev.corn.cornbackend.utils.validators.interfaces.LaterThan;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;

public class LaterThanValidator implements ConstraintValidator<LaterThan, Object> {
    private String firstDateGetterName = null;
    private String secondDateGetterName = null;


    @Override
    public final void initialize(LaterThan constraintAnnotation) {
        this.firstDateGetterName = constraintAnnotation.firstDateGetterName();
        this.secondDateGetterName = constraintAnnotation.secondDateGetterName();

        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public final boolean isValid(Object object, ConstraintValidatorContext context) {
        if (object == null) {
            return false;
        }

        try {
            Method firstDateGetter = object.getClass().getDeclaredMethod(firstDateGetterName);
            Method secondDateGetter = object.getClass().getDeclaredMethod(secondDateGetterName);

            Object firstDateValue = firstDateGetter.invoke(object);
            Object secondDateValue = secondDateGetter.invoke(object);

            if (firstDateValue == null || secondDateValue == null) {
                return true;
            }

            if (firstDateValue instanceof LocalDate firstDate && secondDateValue instanceof LocalDate secondDate) {
                return secondDate.isAfter(firstDate);
            }
            return false;

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return false;
        }
    }
}
