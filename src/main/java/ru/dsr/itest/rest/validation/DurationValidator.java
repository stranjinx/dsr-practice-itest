package ru.dsr.itest.rest.validation;

import ru.dsr.itest.rest.dto.TestHistoryDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DurationValidator implements ConstraintValidator<Duration, TestHistoryDto> {
    private Duration.Target target;

    @Override
    public void initialize(Duration duration) {
        this.target = duration.target();
    }
    @Override
    public boolean isValid(TestHistoryDto value, ConstraintValidatorContext context) {
        return target.isValid(value.getTimeStart(), value.getTimeEnd());
    }
}
