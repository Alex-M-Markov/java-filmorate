package ru.yandex.practicum.filmorate.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class User {
    private Long id;
    @Email(message = "Use a valid e-mail address")
    private String email;
    @NotBlank(message = "Login cannot be empty")
    private final String login;
    private String name;
    @Past(message = "Birthday should be in the past")
    private LocalDate birthday;
    private Set<Long> listOfFriends;

    public User(String email, String login, String name, LocalDate birthday) {
        this.id = InMemoryUserStorage.getUserIdCounter();
        this.email = email;
        this.login = login;
        this.name = checkName(name, login);
        this.birthday = birthday;
        this.listOfFriends = new HashSet<>();
    }

    private String checkName(String name, String login) {
        if (name.isBlank()) {
            return login;
        }
        return name;
    }

}


