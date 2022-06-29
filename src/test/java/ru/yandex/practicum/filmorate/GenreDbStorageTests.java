package ru.yandex.practicum.filmorate;


import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreDbStorage;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GenreDbStorageTests {

    private final GenreDbStorage genreDbStorage;

    @Test
    void getGenre() {
        Optional<Genre> genre = genreDbStorage.getGenre(4);
        assertThat(genre)
                .isPresent()
                .hasValueSatisfying(x ->
                        assertThat(x).hasFieldOrPropertyWithValue("name", "Триллер")
                );
    }

    @Test
    void getGenres() {
        List<Genre> genres = genreDbStorage.getAllGenres();
        assertThat(genres.size()).isEqualTo(6);
    }

}