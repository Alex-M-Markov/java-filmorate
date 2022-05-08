package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public void addLike(Long id, Long userId) {
        Film film = getFilms().get(id);
        film
                .getLikes()
                .add(userId);
        update(film);
    }

    public void deleteLike(Long id, Long userId) {
        Film film = getFilms().get(id);
        film
                .getLikes()
                .remove(userId);
        update(film);
    }

    public List<Film> getTopRatedFilms(Integer count) {
        Map<Long, Film> films = inMemoryFilmStorage.getFilms();
        return films.values().stream()
                .sorted((p0, p1) -> {
                    Integer firstFilmLikes = p0.getLikes().size();
                    Integer secondFilmLikes = p1.getLikes().size();
                    return firstFilmLikes.compareTo(secondFilmLikes);
                })
                .limit(count)
                .collect(Collectors.toList());
    }

    public Film getFilmById(Long id) {
        return inMemoryFilmStorage.getFilms().get(id);
    }

}
