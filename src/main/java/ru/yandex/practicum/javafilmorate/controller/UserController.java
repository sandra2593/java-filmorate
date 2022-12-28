package ru.yandex.practicum.javafilmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.exception.UserAlreadyExistException;
import ru.yandex.practicum.javafilmorate.exception.ValidationException;
import ru.yandex.practicum.javafilmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {
    private final HashMap<Integer, User> users = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private int userCounter = 0;

    @GetMapping("/users")
    public List<User> findAll() {
        List<User> usersList = users.values().stream().collect(Collectors.toList());
        return usersList;
    }

    @PostMapping(value = "/users")
    public User create(@Valid @RequestBody User user) {
        userCounter++;
        user.setId(userCounter);
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может содержать пробелы");
        } else if (users.containsKey(user.getId())) {
            throw new UserAlreadyExistException("Такой пользователь уже есть");
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

    @PutMapping(value = "/users")
    public User update(@Valid @RequestBody User user) {
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может содержать пробелы");
        } else if (!users.containsKey(user.getId())) {
            throw new UserAlreadyExistException("Такой юзера нет в списке");
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
}
