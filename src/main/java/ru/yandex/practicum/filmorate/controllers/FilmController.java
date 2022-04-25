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
    private static int filmIdCounter = 0;

    @PostMapping("/film")
    public Film create(@Valid @RequestBody Film film) {
        log.info("Received request for a new film");
        films.put(filmIdCounter++, film);
        return film;
    }

    @PutMapping("/film")
    public Film update(@Valid @RequestBody Integer id, @RequestBody Film film) {
        if (!films.containsKey(id)) {
            throw new InputMismatchException("Entry does not exist");
        }
        log.info("Received request for a film update");
        films.put(id, film);
        return film;
    }

    @GetMapping("/films")
    public Map<Integer, Film> getAllFilms() {
        return this.getFilms();
    }

}
