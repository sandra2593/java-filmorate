package ru.yandex.practicum.javafilmorate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.exception.EnumOfExceptions;
import ru.yandex.practicum.javafilmorate.exception.NotFoundException;
import ru.yandex.practicum.javafilmorate.mapper.GenreMapper;
import ru.yandex.practicum.javafilmorate.model.Genre;
import ru.yandex.practicum.javafilmorate.storage.GenreStorage;

import java.util.Collection;

@Component("DbGenreStorage")
public class DbGenreStorage  implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DbGenreStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<Genre> getGenres() {
        String sql = "SELECT id, name FROM d_genre";
        return jdbcTemplate.query(sql, new GenreMapper());
    }

    @Override
    public Genre getGenreById(int id) {
        String sql = "SELECT id, name FROM d_genre WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new GenreMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundException(EnumOfExceptions.NO_GENRE.getExp());
        }
    }
}
