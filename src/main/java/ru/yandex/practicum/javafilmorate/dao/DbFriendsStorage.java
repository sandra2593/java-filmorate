package ru.yandex.practicum.javafilmorate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.exception.EnumOfExceptions;
import ru.yandex.practicum.javafilmorate.exception.NotFoundException;
import ru.yandex.practicum.javafilmorate.mapper.UserMapper;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.storage.FriendsStorage;

import java.util.Collection;

@Component("DbFriendsStorage")
public class DbFriendsStorage implements FriendsStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DbFriendsStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addFriend(int id, int friendId) {
        String sql = "MERGE INTO friendship KEY(user1_id, user2_id) VALUES(?, ?, ?)";
        try {
            jdbcTemplate.update(sql, id, friendId, 2); //непринятая дружба
        } catch (DataAccessException ex) {
            throw new NotFoundException(EnumOfExceptions.UPDATE_FRIEND.getExp());
        }
    }

    @Override
    public void deleteFriend(int id, int friendId) {
        String sql = "DELETE FROM friendship WHERE user1_id = ? AND user2_id = ?";
        int rowsInOp = jdbcTemplate.update(sql, id, friendId);
        if (rowsInOp == 0) {
            throw new NotFoundException(EnumOfExceptions.DELETE_FRIEND.getExp());
        }
    }

    @Override
    public Collection<User> getFriends(int id) {
        String sql = "SELECT u.id, u.email, u.login, u.name, u.birthday  FROM users u JOIN friendship friends ON u.id = friends.user2_id WHERE friends.user1_id = ? ";
        return jdbcTemplate.query(sql, new UserMapper(), id);
    }

    @Override
    public Collection<User> getCommonFriends(int id, int otherId) {
        String sql = "SELECT u.id, u.email, u.login, u.name, u.birthday FROM users u " +
                "JOIN (" +
                "    SELECT f.user2_id AS friend_id " +
                "    FROM friendship AS f JOIN friendship AS f_other ON f.user2_id = f_other.user2_id" +
                "    WHERE f.user1_id = ? AND f_other.user1_id = ? " +
                ") common_friends ON u.id = common_friends.friend_id;";
        return jdbcTemplate.query(sql, new UserMapper(), id, otherId);
    }

}
