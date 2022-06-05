package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.validators.CheckFilmDate;
import ru.yandex.practicum.filmorate.validators.PositiveDuration;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Film {

    private Long id;
    @NotBlank(message = "Name cannot be empty")
    @Size(max = 200, message = "Name should not exceed 200 symbols")
    private String name;
    @NotNull
    @NotBlank
    @Size(max = 200, message = "Description should not exceed 200 symbols")
    private String description;
    @NotNull
    @CheckFilmDate
    private LocalDate releaseDate;
    @PositiveDuration
    private Integer duration;
    private LinkedHashSet<Genre> genres;
    private Mpa mpa;

}