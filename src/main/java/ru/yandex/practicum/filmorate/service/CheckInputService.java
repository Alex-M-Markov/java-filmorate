package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.IllegalInputException;

@Service
public class CheckInputService {

    @Autowired
    public CheckInputService() {
    }

    public void checkInput(Long... id) {
        if (id.length == 1 && id[0] <= 0) {
            throw new IllegalInputException("User id should be positive");
        }
        if (id.length == 2 && id[0] <= 0) {
            throw new IllegalInputException("User id should be positive");
        }
        if (id.length == 2 && id[1] <= 0) {
            throw new IllegalInputException("Friend id should be positive");
        }
    }
}
