package ru.yandex.practicum.filmorate.storage;


import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;
import java.util.Optional;

@Repository
@Data
public class MpaDbStorage implements MpaStorage {

    private final JdbcTemplate jdbcTemplate;

    public static final String GET_ALL_MPAS = "select * from MPA_Rating";

    public static final String GET_MPA = "select * from MPA_Rating "
        + "where Mpa_ID = ?";

    @Autowired
    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Mpa> getAllMpas() {
        return jdbcTemplate.query(GET_ALL_MPAS, (rs, rowNum) -> new Mpa(
                rs.getInt("Mpa_ID"),
                rs.getString("Short_Name")
            )
        );
    }

    @Override
    public Optional<Mpa> getMpa(Integer mpaId) {
        return jdbcTemplate.query(GET_MPA, (rs, rowNum) ->
            new Mpa(
                rs.getInt("Mpa_ID"),
                rs.getString("Short_Name")
            ), mpaId
        ).stream().findAny();
    }

}