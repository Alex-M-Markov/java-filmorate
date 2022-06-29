package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    Film create(Film film);

    Film update(Film film);

    List<Film> getAllFilms();

    List<Film> getTopRatedFilms(Integer count);

    Film getFilmById(Long id);

    void addLike(Long id, Long userId);

    void deleteLike(Long id, Long userId);
}