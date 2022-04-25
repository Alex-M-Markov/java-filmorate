package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.validators.CheckFilmDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.LocalDate;

@Data
public class Film {
    private int filmId;
    @NotBlank(message = "Name cannot be empty")
    @Size(max = 200, message = "Name should not exceed 200 symbols")
    private String name;
    private String description;
    @NotNull
    @CheckFilmDate
    private LocalDate releaseDate;
    @Positive(message = "Duration should be positive")
    private Duration filmDuration;
}
