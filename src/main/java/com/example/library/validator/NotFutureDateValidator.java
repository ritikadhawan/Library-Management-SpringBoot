package com.example.library.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

public class NotFutureDateValidator implements ConstraintValidator<NotFutureDate, Date> {
    @Override
    public void initialize(NotFutureDate constraintAnnotation) {
    }

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        if(date == null || date.equals("")) {
            return true;
        }
        Date currDate = new Date();
        return date.before(currDate);
    }
}
