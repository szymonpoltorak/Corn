package dev.corn.cornbackend.entities.validators;

import dev.corn.cornbackend.entities.validators.interfaces.LaterThan;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;

public class LaterThanValidator implements ConstraintValidator<LaterThan, Object> {
    private String firstDateGetterName;
    private String secondDateGetterName;


    @Override
    public void initialize(LaterThan constraintAnnotation) {
        this.firstDateGetterName = constraintAnnotation.firstDateGetterName();
        this.secondDateGetterName = constraintAnnotation.secondDateGetterName();

        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        Method firstDateGetter;
        Method secondDateGetter;
        try {
            firstDateGetter = object.getClass().getDeclaredMethod(firstDateGetterName);
            secondDateGetter = object.getClass().getDeclaredMethod(secondDateGetterName);

            Object firstDateValue = firstDateGetter.invoke(object);
            Object secondDateValue = secondDateGetter.invoke(object);

            if(firstDateValue == null || secondDateValue == null) {
                return true;
            }

            if(firstDateValue instanceof LocalDate firstDate && secondDateValue instanceof LocalDate secondDate) {
                return secondDate.isAfter(firstDate);
            } else {
                return false;
            }

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return false;
        }


    }
}
