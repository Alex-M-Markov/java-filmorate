package ru.yandex.practicum.filmorate.controllers;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

@RestController
@Data
@Slf4j
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();
    public static int filmIdCounter = 1;

    public static int getFilmIdCounter() {
        return filmIdCounter;
    }

    @PostMapping("/films")
    public Film create(@Valid @RequestBody Film film) {
        log.info("Received request for a new film");
        films.put(film.getId(), film);
        filmIdCounter++;
        return film;
    }

    @PutMapping("/films")
    public Film update(@Valid @RequestBody Film film) {
        if (!films.containsKey(film.getId())) {
            throw new InputMismatchException("Entry does not exist");
        }
        log.info("Received request for a film update");
        films.put(film.getId(), film);
        return film;
    }

    @GetMapping("/films")
    public Map<Integer, Film> getAllFilms() {
        return this.getFilms();
    }

}
