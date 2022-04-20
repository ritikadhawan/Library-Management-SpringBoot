package com.example.library.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotEmptyOrNullValidator implements ConstraintValidator<NotEmptyOrNull, Object> {
    @Override
    public void initialize(NotEmptyOrNull constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object field, ConstraintValidatorContext constraintValidatorContext) {
        return !(field == null || field.equals(""));
    }

}
