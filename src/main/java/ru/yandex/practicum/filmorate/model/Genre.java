package ru.yandex.practicum.filmorate.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Genre {

    private final Integer id;
    private final String name;

    public Genre(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

}