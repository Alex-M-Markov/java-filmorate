package ru.yandex.practicum.filmorate.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.validators.CheckFilmDate;
import ru.yandex.practicum.filmorate.validators.PositiveDuration;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Film {
    private Integer id;
    @NotBlank(message = "Name cannot be empty")
    @Size(max = 200, message = "Name should not exceed 200 symbols")
    private String name;
    private String description;
    @NotNull
    @CheckFilmDate
    private LocalDate releaseDate;
    @PositiveDuration
    private Duration duration;


    public Film(String name, String description, LocalDate releaseDate, Duration duration) {
        this.id = InMemoryFilmStorage.getFilmIdCounter();
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }
}
