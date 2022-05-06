package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Map;

@Service
public class FilmService {
    private final FilmStorage inMemoryFilmStorage;

    @Autowired
    public FilmService(InMemoryFilmStorage inMemoryFilmStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
    }

    public Map<Long, Film> getFilms() {
        return inMemoryFilmStorage.getFilms();
    }

    public Film create(Film Film) {
        return inMemoryFilmStorage.create(Film);
    }

    public Film update(Film Film) {
        return inMemoryFilmStorage.update(Film);
    }

}
