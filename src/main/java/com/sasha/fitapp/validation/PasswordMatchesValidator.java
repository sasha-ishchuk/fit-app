package com.sasha.fitapp.validation;

import com.sasha.fitapp.validation.annotaion.PasswordMatches;

import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    private String firstField;
    private String secondField;

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        firstField = constraintAnnotation.first();
        secondField = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = true;
        try {
            final Object firstObj = BeanUtils.getProperty(o, firstField);
            final Object secondObj = BeanUtils.getProperty(o, secondField);

            valid =  firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
        }
        catch (final Exception ignore) {
            // ignore
        }

        return valid;
    }
}
