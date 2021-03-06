package com.example.library.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class NotFutureDateValidator implements ConstraintValidator<NotFutureDate, String> {
    @Override
    public void initialize(NotFutureDate constraintAnnotation) {
    }

    @Override
    public boolean isValid(String date, ConstraintValidatorContext constraintValidatorContext) {
        if(date == null || date.equals("")) {
            return true;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date.trim());
            return LocalDate.parse(date).isBefore(LocalDate.now());
        } catch (ParseException pe) {
            return true;
        }

    }
}
