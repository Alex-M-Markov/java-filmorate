package ru.yandex.practicum.filmorate.storage;

import java.util.List;
import java.util.Optional;

import ru.yandex.practicum.filmorate.model.Mpa;

public interface MpaStorage {

    Optional<Mpa> getMpa(Integer mpaId);

    List<Mpa> getAllMpas();
}