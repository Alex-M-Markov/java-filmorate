package ru.yandex.practicum.filmorate.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PositiveDurationValidator implements ConstraintValidator<PositiveDuration, Integer> {

    @Override
    public boolean isValid(Integer duration, ConstraintValidatorContext context) {
        return duration >= 0;
    }
}