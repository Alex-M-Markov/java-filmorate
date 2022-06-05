package ru.yandex.practicum.filmorate.storage;

import java.util.List;
import ru.yandex.practicum.filmorate.model.Film;

public interface FilmStorage {

    Film create(Film film);

    Film update(Film film);

    List<Film> getAllFilms();

    List<Film> getTopRatedFilms(Integer count);

    Film getFilmById(Long id);

    void addLike(Long id, Long userId);

    void deleteLike(Long id, Long userId);
}