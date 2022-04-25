package ru.yandex.practicum.filmorate.controllers;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

@RestController
@Data
@Slf4j
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    private static int userIdCounter = 0;

    @PostMapping("/user")
    public User create(@Valid @RequestBody User user) {
        log.info("Received request for a new user");
        users.put(userIdCounter++, user);
                return user;
    }

    @PutMapping("/user")
    public User update(@Valid @RequestBody Integer id, @RequestBody User user) {
        if (!users.containsKey(id)) {
            throw new InputMismatchException("Entry does not exist");
        }
        log.info("Received request for a user update");
        users.put(id, user);
        return user;
    }

    @GetMapping("/users")
    public Map<Integer, User> getAllUsers() {
        return this.getUsers();
    }
}

