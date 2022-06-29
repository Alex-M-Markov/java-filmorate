package ru.yandex.practicum.filmorate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.CheckInputService;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final CheckInputService checkInputService;
    private final static String NAME_OF_USER_ENTITY_FOR_CHECKING = "User";

    @Autowired
    public UserController(UserService userService, CheckInputService checkInputService) {
        this.userService = userService;
        this.checkInputService = checkInputService;
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        return userService.create(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        return userService.update(user);
    }

    @GetMapping
    public Collection<User> getAllUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        checkInputService.checkInput(NAME_OF_USER_ENTITY_FOR_CHECKING, id);
        return userService.getUserById(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addToFriends(@PathVariable Long id, @PathVariable Long friendId) {
        checkInputService.checkInput(NAME_OF_USER_ENTITY_FOR_CHECKING, id, friendId);
        userService.sendFriendshipRequest(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFromFriends(@PathVariable Long id, @PathVariable Long friendId) {
        checkInputService.checkInput(NAME_OF_USER_ENTITY_FOR_CHECKING, id, friendId);
        userService.deleteFromFriends(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getFriendsList(@PathVariable Long id) {
        checkInputService.checkInput(NAME_OF_USER_ENTITY_FOR_CHECKING, id);
        return userService.getFriendsList(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getMutualFriendsList(@PathVariable Long id, @PathVariable Long otherId) {
        checkInputService.checkInput(NAME_OF_USER_ENTITY_FOR_CHECKING, id, otherId);
        return userService.getMutualFriendsList(id, otherId);
    }

}