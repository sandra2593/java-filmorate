package ru.yandex.practicum.javafilmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.service.UserService;
import ru.yandex.practicum.javafilmorate.storage.UserStorage;

import javax.validation.Valid;
import java.util.List;


@RestController
public class UserController {
    private final String QUERY_PATH = "/users";
    private final UserService userService;
    private final UserStorage userStorage;

    @Autowired
    public UserController(UserService userService, UserStorage userStorage) {
        this.userService = userService;
        this.userStorage = userStorage;
    }

    @GetMapping(QUERY_PATH)
    public List<User> findAll() {
        return userStorage.findAll();
    }

    @PostMapping(value = QUERY_PATH)
    public User create(@Valid @RequestBody User user) {
       return userStorage.create(user);
    }

    @PutMapping(value = QUERY_PATH)
    public User update(@Valid @RequestBody User user) {
        return userStorage.update(user);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) { userService.addNewFriend(id, friendId); }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable int id, @PathVariable int friendId) { userService.deleteFriend(id, friendId); }

    @GetMapping("/users/{id}/friends")
    public List<User> getFriends(@PathVariable int id) { return userService.getFriendList(id); }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable int id, @PathVariable int otherId) { return userService.getCommonFriendsList(id, otherId);}

    @GetMapping("/users/{id}")
    public User getUserId(@PathVariable int id) { return userStorage.getUserId(id); }

}
