package ru.yandex.practicum.filmorate.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = PositiveDurationValidator.class)
@Documented
public @interface PositiveDuration {

    String message() default "{FilmDuration.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

