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
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
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
    private Set<Long> likes;


    public Film(String name, String description, LocalDate releaseDate, Integer duration) {
        this.id = InMemoryFilmStorage.getFilmIdCounter();
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.likes = new HashSet<>();
    }
}
