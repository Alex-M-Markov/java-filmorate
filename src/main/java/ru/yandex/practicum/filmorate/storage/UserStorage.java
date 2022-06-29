package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    User create(User user);

    User update(User user);

    List<User> getAllUsers();

    User getUserById(Long id);

    void sendFriendshipRequest(Long userId, Long friendId);

    void approveFriendshipRequest(Long userId, Long friendId);

    void deleteFromFriends(Long userId, Long friendId);

    List<User> getFriendsList(Long userId);

    List<User> getMutualFriendsList(Long userId, Long friendId);
}