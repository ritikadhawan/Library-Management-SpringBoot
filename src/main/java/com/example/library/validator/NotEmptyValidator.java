package com.example.library.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotEmptyValidator implements ConstraintValidator<NotEmpty, String> {
    @Override
    public void initialize(NotEmpty constraintAnnotation) {
    }

    @Override
    public boolean isValid(String field, ConstraintValidatorContext constraintValidatorContext) {
        return !field.isEmpty();
    }
}
