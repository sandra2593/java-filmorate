package ru.yandex.practicum.javafilmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.storage.FriendsStorage;

import java.util.Collection;

@Service
public class FriendsService {
    private final FriendsStorage friendsStorage;

    @Autowired
    public FriendsService(@Qualifier("DbFriendsStorage") FriendsStorage friendsStorage) {
        this.friendsStorage = friendsStorage;
    }

    public void addFriend(int id, int friendId) {
        friendsStorage.addFriend(id, friendId);
    }

    public void deleteFriend(int id, int friendId) {
        friendsStorage.deleteFriend(id, friendId);
    }

    public Collection<User> getFriends(int id) {
        return friendsStorage.getFriends(id);
    }

    public Collection<User> getCommonFriends(int id, int otherId) {
        return friendsStorage.getCommonFriends(id, otherId);
    }
}
