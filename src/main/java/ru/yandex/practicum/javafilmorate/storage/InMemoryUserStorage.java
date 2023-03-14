package ru.yandex.practicum.javafilmorate.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.controller.UserController;
import ru.yandex.practicum.javafilmorate.exception.EnumOfExceptions;
import ru.yandex.practicum.javafilmorate.exception.NotFoundException;
import ru.yandex.practicum.javafilmorate.exception.UserAlreadyExistException;
import ru.yandex.practicum.javafilmorate.exception.ValidationException;
import ru.yandex.practicum.javafilmorate.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InMemoryUserStorage implements UserStorage{
    private final HashMap<Integer, User> users = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private int userCounter = 0;

    public List<User> findAll() {
        List<User> usersList = users.values().stream().collect(Collectors.toList());
        return usersList;
    }

    public User create(User user) {
        userCounter++;
        user.setId(userCounter);
        if (user.getLogin().contains(" ")) {
            throw new ValidationException(EnumOfExceptions.NO_SPACE_IN_USER.getExp());
        } else if (users.containsKey(user.getId())) {
            throw new UserAlreadyExistException(EnumOfExceptions.DOUBLE_USER.getExp());
        } else if ((user.getName() == null) | (user.getName() == "")) {
            user.setName(user.getLogin());
            log.debug("Создан пользователь.");
            users.put(user.getId(),user);
            return users.get(user.getId());
        } else {
            log.debug("Создан пользователь.");
            users.put(user.getId(),user);
            return users.get(user.getId());
        }
    }

    public User update(User user) {
        if (user.getLogin().contains(" ")) {
            throw new ValidationException(EnumOfExceptions.NO_SPACE_IN_USER.getExp());
        } else if (!users.containsKey(user.getId())) {
            throw new UserAlreadyExistException(EnumOfExceptions.NOT_IN_LIST.getExp());
        } else if ((user.getName() == null) | (user.getName() == "")) {
            user.setName(user.getLogin());
            log.debug("Обновлен фильм.");
            users.put(user.getId(),user);
            return users.get(user.getId());
        } else {
            log.debug("Обновлен фильм.");
            users.put(user.getId(),user);
            return users.get(user.getId());
        }
    }

    public User getUserId(int id) {
        if (users.get(id) == null) {
            throw new NotFoundException(EnumOfExceptions.NO_USER.getExp());
        }
        return users.get(id);
    }
}
