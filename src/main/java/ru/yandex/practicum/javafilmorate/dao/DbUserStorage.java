package ru.yandex.practicum.javafilmorate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.exception.EnumOfExceptions;
import ru.yandex.practicum.javafilmorate.exception.NotFoundException;
import ru.yandex.practicum.javafilmorate.mapper.UserMapper;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.storage.UserStorage;

import java.util.List;
import java.util.Map;

@Component("DbUserStorage")
public class DbUserStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DbUserStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User create(User newUser) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        int generatedUserId = simpleJdbcInsert.executeAndReturnKey(
                Map.of(
                        "email", newUser.getEmail(),
                        "login", newUser.getLogin(),
                        "name", newUser.getName(),
                        "birthday", newUser.getBirthday()
                )).intValue();
        newUser.setId(generatedUserId);

        return getUserId(newUser.getId());
    }

    @Override
    public User update(User newUser) {
        String sql = "UPDATE users SET email = ?, login = ?, name = ?, birthday = ? WHERE id = ?";
        int rowsInOp = jdbcTemplate.update(sql, newUser.getEmail(), newUser.getLogin(), newUser.getName(), newUser.getBirthday(), newUser.getId());

        if (rowsInOp != 0) {
            return getUserId(newUser.getId());
        } else {
            throw new NotFoundException(EnumOfExceptions.NO_USER.getExp());
        }

    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT id, email, login, name, birthday FROM users";
        return jdbcTemplate.query(sql, new UserMapper());
    }

    @Override
    public User getUserId(int id) {
        String sql = "SELECT id, email, login, name, birthday FROM users WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new UserMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundException(EnumOfExceptions.NO_USER.getExp());
        }
    }
    
}
