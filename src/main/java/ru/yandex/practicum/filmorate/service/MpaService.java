package ru.yandex.practicum.filmorate.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.IllegalInputException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

@Service
public class MpaService {

    @Qualifier("MpaDbStorage")
    private final MpaStorage mpaStorage;

    @Autowired
    public MpaService(MpaStorage mpaStorage) {
        this.mpaStorage = mpaStorage;
    }

    public Mpa getMpa(Integer mpaId) {
        return mpaStorage.getMpa(mpaId).orElseThrow(() -> new IllegalInputException("MPA not found"));

    }

    public List<Mpa> getAllMpa() {
        return mpaStorage.getAllMpas();
    }

}