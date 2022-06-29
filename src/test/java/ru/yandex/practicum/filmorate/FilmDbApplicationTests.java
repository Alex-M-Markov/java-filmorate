package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.exceptions.IllegalInputException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FilmDbApplicationTests {

    private final FilmDbStorage filmDbStorage;
    private final MpaStorage mpaStorage;

    @Test
    public void createFilmTest() {
        filmDbStorage.create(returnFilmForTesting());
        assertThat(filmDbStorage.getFilmById(4L).getName()).isEqualTo("The Godfather");
    }

    @Test
    public void updateFilmTest() {
        filmDbStorage.create(returnFilmForTesting());
        Film film = filmDbStorage.getFilmById(1L);
        film.setDuration(200);
        filmDbStorage.update(film);
        assertThat(filmDbStorage.getFilmById(1L).getDuration()).isEqualTo(200);
    }

    @Test
    public void getFilmById() {
        filmDbStorage.create(returnFilmForTesting());
        filmDbStorage.create(returnFilm2ForTesting());
        assertThat(filmDbStorage.getFilmById(5L).getName()).isEqualTo(returnFilm2ForTesting().getName());
    }

    @Test
    public void getAllFilms() {
        filmDbStorage.create(returnFilmForTesting());
        filmDbStorage.create(returnFilm2ForTesting());
        assertThat(filmDbStorage.getAllFilms().size()).isEqualTo(5);
    }

    @Test
    public void getTopRatedFilms() {
        filmDbStorage.addLike(2L, 3L);
        filmDbStorage.addLike(2L, 2L);
        filmDbStorage.addLike(2L, 1L);
        List<Film> topRatedFilm = filmDbStorage.getTopRatedFilms(1);
        assertThat(topRatedFilm.size()).isEqualTo(1);
        for (Film film : topRatedFilm) {
            assertThat(film.getName()).isEqualTo("Avatar");
        }
    }

    public Film returnFilmForTesting() {
        return new Film(null, "The Godfather",
                "American crime film directed by Francis Ford Coppola", LocalDate.of(1972, 3, 14),
                175, null, getMpaFromOptionalById(4));
    }

    private Mpa getMpaFromOptionalById(Integer id) {
        return mpaStorage.getMpa(id).orElseThrow(() -> new IllegalInputException("no such MPA found"));
    }

    public Film returnFilm2ForTesting() {
        return new Film(null, "The Green Mile",
                "American magical realism drama film written and directed by Frank Darabont",
                LocalDate.of(1999, 12, 10),
                189, null, getMpaFromOptionalById(1));
    }

}