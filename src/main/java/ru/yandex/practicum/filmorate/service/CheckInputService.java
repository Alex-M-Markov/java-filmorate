package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.IllegalInputException;

@Service
public class CheckInputService {

    public void checkInput(String type, Long... id) {
        if (type.equals("User")) {
            if ((id.length == 1 && id[0] <= 0) || id.length == 2 && id[0] <= 0) {
                throw new IllegalInputException("User id should be positive");
            }
            if (id.length == 2 && id[1] <= 0) {
                throw new IllegalInputException("Friend id should be positive");
            }
        } else if (type.equals("Film")) {
            if ((id.length == 1 && id[0] <= 0) || id.length == 2 && id[0] <= 0) {
                throw new IllegalInputException("Film id should be positive");
            }
            if (id.length == 2 && id[1] <= 0) {
                throw new IllegalInputException("User id should be positive");
            }
        }
    }
}
