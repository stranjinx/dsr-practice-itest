package ru.dsr.itest.rest.validation;

import org.hibernate.validator.internal.constraintvalidators.bv.size.SizeValidatorForArray;
import ru.dsr.itest.rest.request.TestDuration;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DurationValidator implements ConstraintValidator<Duration, TestDuration> {
    private Duration.Target target;

    @Override
    public void initialize(Duration duration) {
        this.target = duration.target();
    }
    @Override
    public boolean isValid(TestDuration value, ConstraintValidatorContext context) {
        return target.isValid(value.getTimeStart(), value.getTimeEnd());
    }
}
