package ru.yandex.practicum.javafilmorate.storage;

import ru.yandex.practicum.javafilmorate.model.User;

import java.util.List;

public interface UserStorage {
    User create(User newUser);

    User update(User newUser);

    List<User> findAll();

    User getUserId(int id);
}
