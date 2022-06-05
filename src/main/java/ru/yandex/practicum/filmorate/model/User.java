package ru.yandex.practicum.filmorate.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class User {

    private Long id;
    @NotBlank(message = "Login cannot be empty")
    @Size(max = 50, message = "Login should not exceed 50 symbols")
    private String login;
    @Size(max = 100, message = "Name should not exceed 100 symbols")
    private String name;
    @Email(message = "Use a valid e-mail address")
    @Size(max = 50, message = "E-mail should not exceed 50 symbols")
    private String email;
    @Past(message = "Birthday should be in the past")
    private LocalDate birthday;
    private HashSet<Friendship> friends;


    public User(String login, String name, String email, LocalDate birthday) {
        this.id = 1L;
        this.login = login;
        this.name = checkName(name, login);
        this.email = email;
        this.birthday = birthday;
        this.friends = new HashSet<>();
    }

    private String checkName(String name, String login) {
        if (name.isBlank()) {
            return login;
        }
        return name;
    }

}