package com.example.library.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SizeValidator implements ConstraintValidator<Size, String> {

    private int min;
    private int max;

    @Override
    public void initialize(Size constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s== null || s.equals("")) {
            return true;
        }

        return s.length() >= min && s.length() <= max;
    }
}
