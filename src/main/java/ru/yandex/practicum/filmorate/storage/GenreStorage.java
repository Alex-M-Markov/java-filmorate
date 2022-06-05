package ru.yandex.practicum.filmorate.storage;

import java.util.List;
import java.util.Optional;

import ru.yandex.practicum.filmorate.model.Genre;

public interface GenreStorage {

    Optional<Genre> getGenre(Integer genreId);

    List<Genre> getAllGenres();

}