package ru.yandex.practicum.javafilmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.storage.FriendsStorage;
import ru.yandex.practicum.javafilmorate.storage.UserStorage;

import javax.validation.Valid;
import java.util.Collection;


@RestController
public class UserController {

    private final UserStorage userStorage;
    private final FriendsStorage friendsStorage;

    @Autowired
    public UserController(FriendsStorage friendsStorage, @Qualifier("DbUserStorage")  UserStorage userStorage) {
        this.friendsStorage = friendsStorage;
        this.userStorage = userStorage;
    }

    @GetMapping("/users")
    public Collection<User> findAll() {
        return userStorage.findAll();
    }

    @PostMapping(value = "/users")
    public User create(@Valid @RequestBody User user) {
       return userStorage.create(user);
    }

    @PutMapping(value = "/users")
    public User update(@Valid @RequestBody User user) {
        return userStorage.update(user);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        friendsStorage.addFriend(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable int id, @PathVariable int friendId) {
        friendsStorage.deleteFriend(id, friendId);
    }

    @GetMapping("/users/{id}/friends")
    public Collection<User> getFriends(@PathVariable int id) {
        return friendsStorage.getFriends(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public Collection<User> getCommonFriends(@PathVariable int id, @PathVariable int otherId) {
        return friendsStorage.getCommonFriends(id, otherId);
    }

    @GetMapping("/users/{id}")
    public User getUserId(@PathVariable int id) {
        return userStorage.getUserId(id);
    }

}
