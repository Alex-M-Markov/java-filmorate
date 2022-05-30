package ru.yandex.practicum.filmorate.storage;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.IllegalInputException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.Map;

@Data
@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    private static Long userIdCounter = 1L;

    public static Long getUserIdCounter() {
        return userIdCounter;
    }

    public Map<Long, User> getUsers() {
        return users;
    }

    public User create(User user) {
        log.info("Received request for a new user");
        users.put(user.getId(), user);
        userIdCounter++;
        return user;
    }

    public User update(User user) {
        if (!users.containsKey(user.getId())) {
            throw new IllegalInputException("Entry does not exist");
        }
        log.info("Received request for a user update");
        users.put(user.getId(), user);
        return user;
    }

}
