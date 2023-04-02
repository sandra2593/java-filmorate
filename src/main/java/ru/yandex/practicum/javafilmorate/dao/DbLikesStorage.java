package ru.yandex.practicum.javafilmorate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.exception.EnumOfExceptions;
import ru.yandex.practicum.javafilmorate.exception.NotFoundException;
import ru.yandex.practicum.javafilmorate.storage.LikesStorage;

@Component("DbLikesStorage")
public class DbLikesStorage implements LikesStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DbLikesStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addLike(int id, int userId) {
        String sqlInto = "MERGE INTO likes KEY(user_id, film_id) VALUES (?, ?)";
        String sqlUpdate = "UPDATE films SET rate = rate + 1 WHERE id = ?";
        try {
            jdbcTemplate.update(sqlInto, userId, id);
            jdbcTemplate.update(sqlUpdate, id);
        } catch (DataAccessException ex) {
            throw new NotFoundException(EnumOfExceptions.UPDATE_LIKES.getExp());
        }
    }

    @Override
    public void deleteLike(int id, int userId) {
        String sqlDelete = "DELETE FROM likes WHERE user_id = ? AND film_id = ?";
        String sqlUpdate = "UPDATE films SET rate = rate - 1 WHERE id = ?";
        int rowsInOp = jdbcTemplate.update(sqlDelete, userId, id);
        if (rowsInOp != 0) {
            jdbcTemplate.update(sqlUpdate, id);
        } else {
            throw new NotFoundException(EnumOfExceptions.DELETE_LIKES.getExp());
        }
    }
}
