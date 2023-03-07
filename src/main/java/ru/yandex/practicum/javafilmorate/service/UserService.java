package ru.yandex.practicum.javafilmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.storage.UserStorage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addNewFriend(int id, int newFriendId) {
        User user = userStorage.getUserId(id);
        User friend = userStorage.getUserId(newFriendId);

        user.getFriendSet().add(friend.getId());
        friend.getFriendSet().add(user.getId());
    }

    public void deleteFriend(int id, int friendId) {
        User user = userStorage.getUserId(id);
        User friend = userStorage.getUserId(friendId);

        user.getFriendSet().remove(friend.getId());
        friend.getFriendSet().remove(user.getId());
    }

    public List<User> getFriendList(int id) {
        Set<Integer> userSet = userStorage.getUserId(id).getFriendSet();
        return userSet.stream().map(userStorage::getUserId).collect(Collectors.toList());
    }

    public List<User> getCommonFriendsList(int id, int id2) {
        Set<Integer> userSet = userStorage.getUserId(id).getFriendSet();
        Set<Integer> userSet2 = userStorage.getUserId(id2).getFriendSet();
        Set<Integer> intesecSet = new HashSet<>(userSet);
        intesecSet.retainAll(userSet2);
        return intesecSet.stream().map(userStorage::getUserId).collect(Collectors.toList());
    }

}
