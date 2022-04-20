package com.example.library.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotEmptyOrNullValidator implements ConstraintValidator<NotEmptyOrNull, String> {
    @Override
    public void initialize(NotEmptyOrNull constraintAnnotation) {
    }

    @Override
    public boolean isValid(String field, ConstraintValidatorContext constraintValidatorContext) {
        return !(field == null || field.isEmpty());
    }
}
