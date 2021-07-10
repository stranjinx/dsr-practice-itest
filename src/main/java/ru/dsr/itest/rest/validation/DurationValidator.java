package ru.dsr.itest.rest.validation;

import ru.dsr.itest.rest.request.TestDuration;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DurationValidator implements ConstraintValidator<Duration, TestDuration> {
    @Override
    public boolean isValid(TestDuration value, ConstraintValidatorContext context) {
        return value.getTimeStart().before(value.getTimeEnd());
    }
}
