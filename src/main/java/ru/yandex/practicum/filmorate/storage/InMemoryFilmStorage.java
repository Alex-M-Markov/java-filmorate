package ru.yandex.practicum.filmorate.storage;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

@Data
@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    public static int filmIdCounter = 1;

    public static int getFilmIdCounter() {
        return filmIdCounter;
    }

    public Map<Integer, Film> getFilms() {
        return films;
    }

    public Film create(Film film) {
        log.info("Received request for a new film");
        films.put(film.getId(), film);
        filmIdCounter++;
        return film;
    }

    public Film update(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new InputMismatchException("Entry does not exist");
        }
        log.info("Received request for a film update");
        films.put(film.getId(), film);
        return film;
    }
}