package ru.yandex.practicum.filmorate.storage;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.IllegalInputException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


@Repository
@Data
@Primary
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;
    private final MpaStorage mpaStorage;
    public static final String CREATE_FILM = "insert into FILMS (NAME, DESCRIPTION, "
            + "RELEASE_DATE, DURATION, MPA_ID)"
            + "values (?, ?, ?, ?, ?)";

    public static final String UPDATE_FILM = "update FILMS set NAME = ?, DESCRIPTION = ?, "
            + "RELEASE_DATE = ?, DURATION = ?, MPA_ID = ? "
            + "where FILM_ID = ?";
    public static final String GET_ALL_FILMS = "select * from FILMS";

    public static final String GET_FILM = "select * from FILMS "
            + "where FILM_ID = ?";

    public static final String GET_FILM_ID_BY_NAME = "select Film_ID from Films " +
            "where Name = ?";

    public static final String SAVE_FILM_GENRE = "insert into FILM_GENRE (FILM_ID, GENRE_ID)"
            + "values (?, ?)";

    public static final String DELETE_ALL_FILM_GENRES = "delete from FILM_GENRE "
            + "where FILM_ID = ?";

    public static final String SELECT_GENRES_OF_FILM = "select distinct fg.GENRE_ID, g.NAME "
            + "from FILM_GENRE as fg "
            + "left join GENRE as g on fg.GENRE_ID = g.GENRE_ID "
            + "where fg.FILM_ID = ? "
            + "order by fg.GENRE_ID";

    public static final String SELECT_LIKES_OF_FILM = "select distinct ful.USER_ID "
            + "from FILM_USER_LIKES as ful "
            + "where ful.Film_ID = ?";

    public static final String ADD_LIKE = "insert into FILM_USER_LIKES (FILM_ID, USER_ID)"
            + "values (?, ?)";

    public static final String DELETE_LIKE = "delete from FILM_USER_LIKES "
            + "where FILM_ID = ? and USER_ID = ?";

    public static final String GET_TOP_RATED_FILMS = "select * from FILMS as f "
            + "left join FILM_USER_LIKES as ful on ful.FILM_ID = f.FILM_ID "
            + "group by f.FILM_ID, ful.user_id "
            + "order by count(ful.FILM_ID) desc "
            + "limit ?";


    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate, MpaStorage mpaStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.mpaStorage = mpaStorage;
    }

    @Override
    public Film create(Film film) {
        jdbcTemplate.update(CREATE_FILM, film.getName(), film.getDescription(),
                film.getReleaseDate(), film.getDuration(), film.getMpa().getId());
        if (film.getId() == null) {
            film.setId(getFilmIdByName(film.getName()));
        }
        deleteAllFilmGenres(film);
        saveFilmGenre(film);
        return film;
    }

    private Long getFilmIdByName(String name) {
        return jdbcTemplate.queryForObject(GET_FILM_ID_BY_NAME, (rs, rowNum) ->
                rs.getLong("Film_ID"), name
        );
    }

    @Override
    public Film update(Film film) {
        jdbcTemplate.update(UPDATE_FILM, film.getName(), film.getDescription(),
                film.getReleaseDate(), film.getDuration(), film.getMpa().getId(), film.getId());
        deleteAllFilmGenres(film);
        saveFilmGenre(film);
        return film;
    }

    @Override
    public List<Film> getAllFilms() {
        return jdbcTemplate.query(GET_ALL_FILMS, (rs, rowNum) -> new Film(
                        rs.getLong("Film_ID"),
                        rs.getString("Name"),
                        rs.getString("Description"),
                        rs.getDate("Release_Date").toLocalDate(),
                        rs.getInt("Duration"),
                        selectGenres(rs.getLong("Film_ID")),
                        mpaStorage.getMpa(rs.getInt("Mpa_ID")).orElseThrow
                                (() -> new IllegalInputException("MPA not found"))
                )
        );
    }

    @Override
    public Film getFilmById(Long id) {
        return jdbcTemplate.queryForObject(GET_FILM, (rs, rowNum) ->
                new Film(
                        rs.getLong("Film_ID"),
                        rs.getString("Name"),
                        rs.getString("Description"),
                        rs.getDate("Release_Date").toLocalDate(),
                        rs.getInt("Duration"),
                        selectGenres(rs.getLong("Film_ID")),
                        mpaStorage.getMpa(rs.getInt("Mpa_ID")).orElseThrow(() ->
                                new IllegalInputException("MPA not found"))
                ), id
        );
    }

    @Override
    public void addLike(Long filmId, Long userId) {
        jdbcTemplate.update(ADD_LIKE, filmId, userId);
    }

    @Override
    public void deleteLike(Long filmId, Long userId) {
        jdbcTemplate.update(DELETE_LIKE, filmId, userId);
    }

    private void saveFilmGenre(Film film) {
        if (film.getGenres() != null) {
            Set<Genre> filmGenres = film.getGenres();
            for (Genre genre : filmGenres) {
                jdbcTemplate.update(SAVE_FILM_GENRE, film.getId(), genre.getId());
            }
        }
    }

    private void deleteAllFilmGenres(Film film) {
        jdbcTemplate.update(DELETE_ALL_FILM_GENRES, film.getId());
    }

    private LinkedHashSet<Genre> selectGenres(Long id) {
        List<Genre> genres = jdbcTemplate.query(SELECT_GENRES_OF_FILM, (rs, rowNum) -> new Genre(
                        rs.getInt("Genre_ID"),
                        rs.getString("Name")
                ), id
        );
        if (genres.size() == 0) {
            return null;
        }
        return new LinkedHashSet<>(genres);
    }

    @Override
    public List<Film> getTopRatedFilms(Integer count) {
        return jdbcTemplate.query(GET_TOP_RATED_FILMS, (rs, rowNum) -> new Film(
                        rs.getLong("Film_ID"),
                        rs.getString("Name"),
                        rs.getString("Description"),
                        rs.getDate("Release_Date").toLocalDate(),
                        rs.getInt("Duration"),
                        selectGenres(rs.getLong("Film_ID")),
                        mpaStorage.getMpa(rs.getInt("Mpa_ID")).orElseThrow(() ->
                                new IllegalInputException("MPA not found"))
                ), count
        );
    }

}