package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.IllegalInputException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.List;

@Service
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }


    public Collection<User> getUsers() {
        return userStorage.getAllUsers();
    }

    public User getUserById(Long id) {
        return userStorage.getUserById(id);
    }

    public User create(User user) {
        return userStorage.create(user);
    }

    public User update(User user) {
        if (user.getId() <= 0) {
            throw new IllegalInputException("id is negative");
        }
        return userStorage.update(user);
    }

    public void sendFriendshipRequest(Long userId, Long friendId) {
        userStorage.sendFriendshipRequest(userId, friendId);
    }

    public void approveFriendshipRequest(Long userId, Long friendId) {
        userStorage.approveFriendshipRequest(userId, friendId);
    }

    public void deleteFromFriends(Long userId, Long friendId) {
        userStorage.deleteFromFriends(userId, friendId);
    }

    public List<User> getFriendsList(Long userId) {
        return userStorage.getFriendsList(userId);
    }

    public List<User> getMutualFriendsList(Long userId, Long friendId) {
        return userStorage.getMutualFriendsList(userId, friendId);
    }

}