package ru.yandex.practicum.filmorate.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.IllegalInputException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

@Service
public class GenreService {

    @Qualifier("GenreDbStorage")
    private final GenreStorage genreStorage;

    @Autowired
    public GenreService(GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public Genre getGenre(Integer genreId) {
        return genreStorage.getGenre(genreId).orElseThrow(() -> new IllegalInputException("Genre not found"));
    }

    public List<Genre> getAllGenres() {
        return genreStorage.getAllGenres();
    }

}