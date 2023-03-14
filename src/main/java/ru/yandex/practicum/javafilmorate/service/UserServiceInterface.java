package ru.yandex.practicum.javafilmorate.service;

import ru.yandex.practicum.javafilmorate.model.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface UserServiceInterface {
    void deleteFriend(int id, int friendId);

    List<User> getFriendList(int id);

    List<User> getCommonFriendsList(int id, int id2);
}
