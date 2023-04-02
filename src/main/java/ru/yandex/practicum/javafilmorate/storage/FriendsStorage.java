package ru.yandex.practicum.javafilmorate.storage;

import ru.yandex.practicum.javafilmorate.model.User;

import java.util.Collection;

public interface FriendsStorage {
    void addFriend(int id, int friendId);
    void deleteFriend(int id, int friendId);
    Collection<User> getFriends(int id);
    Collection<User> getCommonFriends(int id, int otherId);
}