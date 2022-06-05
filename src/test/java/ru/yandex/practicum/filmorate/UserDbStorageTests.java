package ru.yandex.practicum.filmorate;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserDbStorage;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserDbStorageTests {

    private final UserDbStorage userStorage;

    @Test
    public void createUserTest() {
        userStorage.create(returnUser1ForTesting());
        userStorage.create(returnUser2ForTesting());

        assertThat(userStorage.getUserById(4L).getName()).isEqualTo("Tiger");
    }

    @Test
    public void updateUserTest() {
        userStorage.create(returnUser1ForTesting());
        userStorage.create(returnUser2ForTesting());
        User friend = userStorage.getUserById(5L);
        User user = userStorage.getUserById(4L);
        user.getFriends().add(new Friendship(user.getId(), friend.getId(), true));
        user.setLogin("WhiteTiger");
        userStorage.update(user);
        assertThat(userStorage.getUserById(4L).getLogin()).isEqualTo("WhiteTiger");
    }

    @Test
    public void getUserByIdTest() {
        userStorage.create(returnUser1ForTesting());
        Optional<User> userOptional = Optional.of(userStorage.getUserById(4L));

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", 4L)
                );
    }

    @Test
    public void getAllUsersTest() {
        List<User> users = userStorage.getAllUsers();
        assertThat(users).hasSize(3);
    }

    private User returnUser1ForTesting() {
        return new User("BlackTiger", "Tiger", "jungle@africa.af",
                LocalDate.of(2020, 2, 2));
    }

    private User returnUser2ForTesting() {
        return new User("Gorilla", "Gor", "grl@zoo.uk",
                LocalDate.of(2000, 11, 14));
    }

    @Test
    public void sendFriendshipRequest() {
        userStorage.sendFriendshipRequest(1L, 2L);
        HashSet<Friendship> friendshipList = userStorage.getUserById(1L).getFriends();
        System.out.println(userStorage.getUserById(1L));
        assertThat(friendshipList).contains((new Friendship(userStorage.getUserById(1L).getId(),
                userStorage.getUserById(2L).getId(), false)));
    }
/*
    @Test
    public void approveFriendshipRequest() {
        userStorage.create(returnUser1ForTesting());
        userStorage.create(returnUser2ForTesting());
        userStorage.sendFriendshipRequest(5L, 6L);
        userStorage.approveFriendshipRequest(6L, 5L);
        HashSet<Friendship> friendshipList = userStorage.getUserById(5L).getFriends();
        assertThat(friendshipList).contains((new Friendship(5L, 6L, true)));
    }*/

    @Test
    public void getFriendsList() {
        userStorage.sendFriendshipRequest(1L, 2L);
        userStorage.approveFriendshipRequest(2L, 1L);
        userStorage.sendFriendshipRequest(1L, 3L);
        userStorage.approveFriendshipRequest(3L, 1L);
        List<User> friendshipList = userStorage.getFriendsList(1L);
        assertThat(friendshipList).contains(userStorage.getUserById(2L));
        assertThat(friendshipList).contains(userStorage.getUserById(3L));
    }

    @Test
    public void getMutualFriendsList() {
        userStorage.sendFriendshipRequest(1L, 2L);
        userStorage.approveFriendshipRequest(2L, 1L);
        userStorage.sendFriendshipRequest(2L, 3L);
        userStorage.approveFriendshipRequest(3L, 2L);
        List<User> mutualFriendsList = userStorage.getMutualFriendsList(1L, 3L);
        assertThat(mutualFriendsList).contains(userStorage.getUserById(2L));
    }
}