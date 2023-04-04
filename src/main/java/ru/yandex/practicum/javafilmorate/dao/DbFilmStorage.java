package ru.yandex.practicum.javafilmorate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.exception.EnumOfExceptions;
import ru.yandex.practicum.javafilmorate.exception.NotFoundException;
import ru.yandex.practicum.javafilmorate.exception.ValidationException;
import ru.yandex.practicum.javafilmorate.mapper.FilmMapper;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.storage.FilmStorage;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component("DbFilmStorage")
public class DbFilmStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DbFilmStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Film create(Film newFilm) {
        if (newFilm.getReleaseDate().isBefore(LocalDate.of(1895,12,28))) {
            throw new ValidationException(EnumOfExceptions.RELEASE_DATE.getExp());
        } else {
            SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                    .withTableName("films")
                    .usingGeneratedKeyColumns("id")
                    .usingColumns("name", "description", "release_date", "duration", "rating_id");

            try {
                int generatedFilmId = simpleJdbcInsert.executeAndReturnKey(
                        Map.of(
                                "name", newFilm.getName(),
                                "description", newFilm.getDescription(),
                                "release_date", newFilm.getReleaseDate(),
                                "duration", newFilm.getDuration(),
                                "rating_id", newFilm.getMpa().getId()
                        )
                ).intValue();
                newFilm.setId(generatedFilmId);
            } catch (DataAccessException ex) {
                throw new NotFoundException(EnumOfExceptions.NO_RATING.getExp());
            }

            if (newFilm.getGenres() != null) {
                String sql = "INSERT INTO film_genre (film_id, genre_id) VALUES (?, ?)";
                jdbcTemplate.batchUpdate(sql,
                        new BatchPreparedStatementSetter() {
                            @Override
                            public void setValues(PreparedStatement ps, int i) throws SQLException {
                                ps.setInt(1, newFilm.getId());
                                ps.setInt(2, newFilm.getGenres().get(i).getId());
                            }

                            @Override
                            public int getBatchSize() {
                                return newFilm.getGenres().size();
                            }
                        });
            }
        }

        return getFilmId(newFilm.getId());
    }

    public Film update(Film newFilm) {
        String sql = "UPDATE films SET rating_id = ?, name = ?, description = ?, release_date = ?, duration = ? WHERE id = ?";

        try {
            int rowsInOp = jdbcTemplate.update(sql, newFilm.getMpa().getId(), newFilm.getName(), newFilm.getDescription(), newFilm.getReleaseDate(), newFilm.getDuration(), newFilm.getId());
            if (rowsInOp == 0) {
                throw new NotFoundException(EnumOfExceptions.NO_FILM.getExp());
            }
        } catch (DataAccessException ex) {
            throw new NotFoundException(EnumOfExceptions.NO_RATING.getExp());
        }

        if (newFilm.getGenres() != null) {
            String sqlDelete = "DELETE FROM film_genre WHERE film_id = ?";
            jdbcTemplate.update(sqlDelete, newFilm.getId());

            // обновим значение списка жанров
            if (newFilm.getGenres().size() > 0) {
                String sqlInto = "MERGE INTO film_genre KEY(film_id, genre_id) VALUES (?, ?)";
                jdbcTemplate.batchUpdate(sqlInto,
                        new BatchPreparedStatementSetter() {
                            @Override
                            public void setValues(PreparedStatement ps, int i) throws SQLException {
                                ps.setLong(1, newFilm.getId());
                                ps.setLong(2, newFilm.getGenres().get(i).getId());
                            }

                            @Override
                            public int getBatchSize() {
                                return newFilm.getGenres().size();
                            }
                        }
                );
            }
        }

        return getFilmId(newFilm.getId());
    }

    public List<Film> findAll() {
        String sql = "SELECT f.id," +
                "       f.rating_id," +
                "       r.name AS rating_name," +
                "       fg.genres_ids," +
                "       fg.genres_names," +
                "       f.name," +
                "       f.description," +
                "       f.release_date," +
                "       f.duration " +
                "FROM films f " +
                "         LEFT JOIN (" +
                "            SELECT fg.film_id," +
                "                   listagg(fg.genre_id, '~') WITHIN GROUP (ORDER BY fg.genre_id) AS genres_ids," +
                "                   listagg(genre.name, '~') WITHIN GROUP (ORDER BY fg.genre_id) AS genres_names " +
                "            FROM film_genre fg " +
                "            JOIN d_genre AS genre ON fg.genre_id = genre.id " +
                "            GROUP BY fg.film_id " +
                "         ) AS fg ON f.id = fg.film_id " +
                "         LEFT JOIN d_rating AS r ON f.rating_id = r.id";

        return jdbcTemplate.query(sql, new FilmMapper());
    }

    public Film getFilmId(int id) {
        String sql = "SELECT f.id," +
                "       f.rating_id," +
                "       r.name AS rating_name," +
                "       fg.genres_ids," +
                "       fg.genres_names," +
                "       f.name," +
                "       f.description," +
                "       f.release_date," +
                "       f.duration " +
                "FROM films f " +
                "         LEFT JOIN (" +
                "            SELECT fg.film_id," +
                "                   listagg(fg.genre_id, '~') WITHIN GROUP (ORDER BY fg.genre_id) AS genres_ids," +
                "                   listagg(genre.name, '~') WITHIN GROUP (ORDER BY fg.genre_id) AS genres_names " +
                "            FROM film_genre fg " +
                "            JOIN d_genre AS genre ON fg.genre_id = genre.id " +
                "            GROUP BY fg.film_id" +
                "         ) AS fg ON f.id = fg.film_id " +
                "         LEFT JOIN d_rating AS r ON f.rating_id = r.id " +
                "WHERE f.id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, new FilmMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundException(EnumOfExceptions.NO_FILM.getExp());
        }
    }

    @Override
    public List<Film> getTopFilms(Integer count) {
        String sql = "SELECT f.id," +
                "       f.rating_id," +
                "       r.name AS rating_name," +
                "       fg.genres_ids," +
                "       fg.genres_names," +
                "       f.name," +
                "       f.description," +
                "       f.release_date," +
                "       f.duration " +
                "FROM films f " +
                "         LEFT JOIN (" +
                "            SELECT fg.film_id," +
                "                   listagg(fg.genre_id, '~') WITHIN GROUP (ORDER BY fg.genre_id) AS genres_ids," +
                "                   listagg(genre.name, '~') WITHIN GROUP (ORDER BY fg.genre_id) AS genres_names " +
                "            FROM film_genre fg " +
                "            JOIN d_genre AS genre ON fg.genre_id = genre.id " +
                "            GROUP BY fg.film_id " +
                "         ) AS fg ON f.id = fg.film_id " +
                "         LEFT JOIN d_rating AS r ON f.rating_id = r.id " +
                "ORDER BY f.rate DESC " +
                "LIMIT ?;";

        return jdbcTemplate.query(sql, new FilmMapper(), count != null ? count : 10);
    }
}
