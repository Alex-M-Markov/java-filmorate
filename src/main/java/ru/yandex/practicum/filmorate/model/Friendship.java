package ru.yandex.practicum.filmorate.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Friendship {

    private final Long userId;
    private final Long friendId;
    private final boolean approved;


    public Friendship(Long userId, Long friendId, boolean approved) {
        this.userId = userId;
        this.friendId = friendId;
        this.approved = approved;
    }
}