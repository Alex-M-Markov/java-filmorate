package ru.yandex.practicum.filmorate.storage;


import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;

@Repository
@Data
public class GenreDbStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;

    public static final String GET_ALL_GENRES = "select * from Genre " +
            "ORDER BY GENRE_ID";

    public static final String GET_GENRE = "select * from Genre "
            + "where GENRE_ID = ?";

    @Autowired
    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Genre> getAllGenres() {
        return jdbcTemplate.query(GET_ALL_GENRES, (rs, rowNum) -> new Genre(
                        rs.getInt("Genre_ID"),
                        rs.getString("Name")
                )
        );
    }

    @Override
    public Optional<Genre> getGenre(Integer genreId) {
        return jdbcTemplate.query(GET_GENRE, (rs, rowNum) ->
                new Genre(
                        rs.getInt("Genre_ID"),
                        rs.getString("Name")
                ), genreId
        ).stream().findAny();
    }

}