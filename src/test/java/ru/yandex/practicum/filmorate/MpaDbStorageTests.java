package ru.yandex.practicum.filmorate;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaDbStorage;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MpaDbStorageTests {

    private final MpaDbStorage mpaStorage;

    @Test
    void getMpa() {
        Optional<Mpa> mpa = mpaStorage.getMpa(3);
        assertThat(mpa)
                .isPresent()
                .hasValueSatisfying(x ->
                        assertThat(x).hasFieldOrPropertyWithValue("name", "PG-13")
                );
    }

    @Test
    void getMpas() {
        List<Mpa> mpas = mpaStorage.getAllMpas();
        assertThat(mpas.size()).isEqualTo(5);
    }

}