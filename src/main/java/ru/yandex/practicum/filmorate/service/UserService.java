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

    public void addToFriends(Long id, Long friendId) {
        establishFriendship(id, friendId);
        establishFriendship(friendId, id);
    }

    public void establishFriendship(Long id, Long friendId) {
        User user = getUsers().get(id);
        user
                .getFriends()
                .add(friendId);
        update(user);
    }

    public void deleteFromFriends(Long id, Long friendId) {
        cancelFriendship(id, friendId);
        cancelFriendship(friendId, id);
    }

    public void cancelFriendship(Long id, Long friendId) {
        User user = getUsers().get(id);
        user
                .getFriends()
                .remove(friendId);
        update(user);
    }

    public List<User> getFriendsList(Long id) {
        Map<Long, User> users = inMemoryUserStorage.getUsers();
        List<User> userFriends = new ArrayList<>();
        Set<Long> userFriendsId = users.get(id).getFriends();

        for (Long friend : userFriendsId) {
            userFriends.add(users.get(friend));
        }
        return userFriends;
    }

    public List<User> getMutualFriendsList(Long id, Long friendId) {
        Map<Long, User> users = inMemoryUserStorage.getUsers();
        List<User> mutualFriends = new ArrayList<>();
        Set<Long> userFriendsId = users.get(id).getFriends();
        Set<Long> friendsOfFriendId = users.get(friendId).getFriends();

        for (Long friend : userFriendsId) {
            if (friendsOfFriendId.contains(friend)) {
                mutualFriends.add(users.get(friend));
            }
        }
        return mutualFriends;
    }

    public User getUserById(Long id) {
        return inMemoryUserStorage.getUsers().get(id);
    }

}
