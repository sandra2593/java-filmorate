package ru.yandex.practicum.javafilmorate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.exception.EnumOfExceptions;
import ru.yandex.practicum.javafilmorate.exception.NotFoundException;
import ru.yandex.practicum.javafilmorate.mapper.RatingMapper;
import ru.yandex.practicum.javafilmorate.model.Rating;
import ru.yandex.practicum.javafilmorate.storage.RatingStorage;

import java.util.List;

@Component("DbRatingStorage")
public class DbRatingStorage  implements RatingStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DbRatingStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Rating> getRatings() {
        String sql = "SELECT id, name FROM d_rating";
        return jdbcTemplate.query(sql, new RatingMapper());
    }

    @Override
    public Rating getRatingById(int id) {
        String sql = "SELECT id, name FROM d_rating WHERE id  = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new RatingMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundException(EnumOfExceptions.NO_RATING.getExp());
        }
    }
}
