package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class UserService {
    private final UserStorage inMemoryUserStorage;

    @Autowired
    public UserService(InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public Map<Long, User> getUsers() {
        return inMemoryUserStorage.getUsers();
    }

    public User create(User user) {
        return inMemoryUserStorage.create(user);
    }

    public User update(User user) {
        return inMemoryUserStorage.update(user);
    }

    public void establishFriendship(Long userId, Long friendId) {
        addToFriends(userId, friendId);
        addToFriends(friendId, userId);
    }

    public void addToFriends(Long userId, Long friendId) {
        User user = getUsers().get(userId);
        user
                .getFriends()
                .add(friendId);
        update(user);
    }

    public void cancelFriendship(Long userId, Long friendId) {
        deleteFromFriends(userId, friendId);
        deleteFromFriends(friendId, userId);
    }

    public void deleteFromFriends(Long userId, Long friendId) {
        User user = getUsers().get(userId);
        user
                .getFriends()
                .remove(friendId);
        update(user);
    }

    public List<User> getFriendsList(Long userId) {
        Map<Long, User> users = inMemoryUserStorage.getUsers();
        List<User> userFriends = new ArrayList<>();
        Set<Long> userFriendsId = users.get(userId).getFriends();

        for (Long id : userFriendsId) {
            userFriends.add(users.get(id));
        }
        return userFriends;
    }

    public List<User> getMutualFriendsList(Long userId, Long friendId) {
        Map<Long, User> users = inMemoryUserStorage.getUsers();
        List<User> mutualFriends = new ArrayList<>();
        Set<Long> userFriendsId = users.get(userId).getFriends();
        Set<Long> friendsOfFriendId = users.get(friendId).getFriends();

        for (Long id : userFriendsId) {
            if (friendsOfFriendId.contains(id)) {
                mutualFriends.add(users.get(id));
            }
        }
        return mutualFriends;
    }

}
