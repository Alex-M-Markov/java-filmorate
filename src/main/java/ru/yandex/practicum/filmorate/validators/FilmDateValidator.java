package ru.yandex.practicum.filmorate.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class FilmDateValidator implements ConstraintValidator<CheckFilmDate, LocalDate> {
    static final LocalDate FIRST_FILM = LocalDate.of(1895, 12, 28);

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        return value.isAfter(FIRST_FILM);
    }
}

