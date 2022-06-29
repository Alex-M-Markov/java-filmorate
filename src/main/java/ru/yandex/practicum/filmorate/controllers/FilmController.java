package ru.yandex.practicum.filmorate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.CheckInputService;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;
    private final CheckInputService checkInputService;
    private static final int TEN_MOST_POPULAR_FILMS = 10;
    private static final String NAME_OF_FILM_ENTITY_FOR_CHECKING = "Film";

    @Autowired
    public FilmController(FilmService filmService, CheckInputService checkInputService) {
        this.filmService = filmService;
        this.checkInputService = checkInputService;
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        return filmService.create(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        return filmService.update(film);
    }

    @GetMapping
    public List<Film> getAllFilms() {
        return filmService.getFilms();
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable Long id) {
        checkInputService.checkInput(NAME_OF_FILM_ENTITY_FOR_CHECKING, id);
        return filmService.getFilmById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable Long id, @PathVariable Long userId) {
        checkInputService.checkInput(NAME_OF_FILM_ENTITY_FOR_CHECKING, id, userId);
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        checkInputService.checkInput(NAME_OF_FILM_ENTITY_FOR_CHECKING, id, userId);
        filmService.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getTopRatedFilms(@RequestParam(required = false) Integer count) {
        return filmService.getTopRatedFilms(
                Objects.requireNonNullElse(count, TEN_MOST_POPULAR_FILMS));
    }

}