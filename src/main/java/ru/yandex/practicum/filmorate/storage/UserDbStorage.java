package ru.yandex.practicum.filmorate.storage;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.IllegalInputException;
import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Repository
@Data
@Primary
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;
    public static final String CREATE_USER = "insert into Users (Login, Name, Email, "
            + "Birth_Date)"
            + "values (?, ?, ?, ?)";
    public static final String UPDATE_USER = "update Users set Login = ?, Name = ?, Email = ?, "
            + "Birth_Date = ? "
            + "where User_ID = ?";
    public static final String GET_ALL_USERS = "select * from Users";

    public static final String GET_USER = "select * from Users "
            + "where User_ID = ?";

    public static final String GET_USER_ID_BY_LOGIN = "select User_ID from Users " +
            "where Login = ?";

    public static final String ADD_FRIENDSHIP = "insert into Friendship (User_ID, Friend_ID, "
            + "Approval)"
            + "values (?, ?, ?)";

    public static final String UPDATE_FRIENDSHIP = "update Friendship set User_ID = ?, "
            + "Friend_ID = ?, Approval = ? "
            + "where User_ID = ? AND Friend_ID = ?";

    public static final String SELECT_FRIENDSHIPS_OF_USER = "select u.User_ID, f.Friend_ID, "
            + "f.Approval "
            + "from Users as u "
            + "left join Friendship as f on f.User_ID = u.User_ID "
            + "where u.User_ID = ? and f.Friend_ID > 0";

    public static final String DELETE_FRIENDSHIP = "delete from Friendship "
            + "where User_ID = ? and Friend_ID = ?";

    public static final String DELETE_ALL_FRIENDSHIPS = "delete from Friendship "
            + "where User_ID = ?";

    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public User create(User user) {
        jdbcTemplate.update(CREATE_USER, user.getLogin(), user.getName(),
                user.getEmail(), user.getBirthday());
        if (user.getFriends().size() != 0) {
            saveUserFriends(user);
        }
        return addUserId(user);
    }

    @Override
    public User update(User user) {
        checkUserID(user);
        jdbcTemplate.update(UPDATE_USER, user.getLogin(), user.getName(), user.getEmail(),
                user.getBirthday(), user.getId());
        if (user.getFriends().size() != 0) {
            updateUserFriends(user);
        }
        return addUserId(user);
    }

    private User addUserId(User user) {
        Long id = jdbcTemplate.queryForObject(GET_USER_ID_BY_LOGIN, (rs, rowNum) ->
                        rs.getLong("User_ID")
                , user.getLogin()
        );
        user.setId(id);
        return user;
    }


    private void checkUserID(User user) {
        if (getUserById(user.getId()) == null) {
            throw new IllegalInputException("User not found");
        }
    }

    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query(GET_ALL_USERS, (rs, rowNum) -> new User(
                        /*  rs.getLong("User_ID"),*/
                        rs.getString("Login"),
                        rs.getString("Name"),
                        rs.getString("Email"),
                        rs.getDate("Birth_Date").toLocalDate()/*,
                        selectFriendship(rs.getLong("User_ID"))*/
                )
        );
    }

    @Override
    public User getUserById(Long id) {
        User user = jdbcTemplate.queryForObject(GET_USER, (rs, rowNum) ->
                new User(
                        rs.getString("Login"),
                        rs.getString("Name"),
                        rs.getString("Email"),
                        rs.getDate("Birth_Date").toLocalDate()
                ), id
        );
        user.setId(id);
        user.setFriends(selectFriendship(id));
        return user;
    }

    private void saveUserFriends(User user) {
        Set<Friendship> userFriends = user.getFriends();
        for (Friendship friendship : userFriends) {
            createFriendship(friendship.getUserId(), friendship.getFriendId(), friendship.isApproved());
        }
    }

    private void updateUserFriends(User user) {
        Set<Friendship> userFriends = user.getFriends();
        jdbcTemplate.update(DELETE_ALL_FRIENDSHIPS, user.getId());
        for (Friendship friendship : userFriends) {
            createFriendship(friendship.getUserId(), friendship.getFriendId(), friendship.isApproved());
        }
    }

    private void createFriendship(Long userId, Long friendId, Boolean approval) {
        jdbcTemplate.update(ADD_FRIENDSHIP, userId, friendId, approval);
    }

    @Override
    public void sendFriendshipRequest(Long userId, Long friendId) {
        createFriendship(userId, friendId, false);
    }

    @Override
    public void approveFriendshipRequest(Long userId, Long friendId) {
        jdbcTemplate.update(ADD_FRIENDSHIP, userId, friendId, true);
        jdbcTemplate.update(UPDATE_FRIENDSHIP, friendId, userId, true, friendId, userId);
    }

    @Override
    public void deleteFromFriends(Long userId, Long friendId) {
        jdbcTemplate.update(DELETE_FRIENDSHIP, userId, friendId);
        jdbcTemplate.update(DELETE_FRIENDSHIP, friendId, userId);
    }

    private HashSet<Friendship> selectFriendship(Long userId) {
        return new HashSet<>(
                jdbcTemplate.query(SELECT_FRIENDSHIPS_OF_USER, (rs, rowNum) -> new Friendship(
                                rs.getLong("User_ID"),
                                rs.getLong("Friend_ID"),
                                rs.getBoolean("Approval")
                        ), userId
                ));
    }

    @Override
    public List<User> getFriendsList(Long userId) {
        Set<Friendship> friendships = selectFriendship(userId);
        return friendships.stream()
                /*.filter(Friendship::isApproved)*/
                .map(Friendship::getFriendId)
                .map(this::getUserById)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getMutualFriendsList(Long userId, Long friendId) {
        Set<Friendship> friendships = selectFriendship(userId);
        Set<Friendship> friendshipsOfFriend = selectFriendship(friendId);
        return friendships.stream()
                /* .filter(Friendship::isApproved)*/
                .map(Friendship::getFriendId)
                .filter(x -> friendshipsOfFriend.stream()
                        /*   .filter(Friendship::isApproved)*/
                        .map(Friendship::getFriendId)
                        .collect(Collectors.toList()).contains(x))
                .map(this::getUserById)
                .collect(Collectors.toList());
    }

}