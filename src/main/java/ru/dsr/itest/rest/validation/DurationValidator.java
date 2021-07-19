package ru.dsr.itest.rest.validation;

import ru.dsr.itest.rest.dto.TestDurationDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DurationValidator implements ConstraintValidator<Duration, TestDurationDto> {
    private Duration.Target target;

    @Override
    public void initialize(Duration duration) {
        this.target = duration.target();
    }
    @Override
    public boolean isValid(TestDurationDto value, ConstraintValidatorContext context) {
        return target.isValid(value.getTimeStart(), value.getTimeEnd());
    }
}
