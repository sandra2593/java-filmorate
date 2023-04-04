package ru.yandex.practicum.javafilmorate.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.javafilmorate.exception.EnumOfExceptions;
import ru.yandex.practicum.javafilmorate.exception.NotFoundException;
import ru.yandex.practicum.javafilmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class DbFriendsStorageTest {
    private final DbFriendsStorage dbFriendsStorage;
    private final DbUserStorage dbUserStorage;
    private User user1 = User.builder()
            .email("email1@example.com")
            .login("login1")
            .name("user1")
            .birthday(LocalDate.of(2023, 4, 1))
            .build();
    private User user2 = User.builder()
            .email("email2@example.com")
            .login("login2")
            .name("user2")
            .birthday(LocalDate.of(2023, 4, 1))
            .build();
    private User user3 = User.builder()
            .email("email3@example.com")
            .login("login3")
            .name("user3")
            .birthday(LocalDate.of(2023, 4, 1))
            .build();

    @Test
    public void testAddDeleteFriend() {
        User userDb1 = dbUserStorage.create(user1);
        User userDb2 = dbUserStorage.create(user2);

        dbFriendsStorage.addFriend(userDb1.getId(), userDb2.getId());
        Collection<User> friends = dbFriendsStorage.getFriends(userDb1.getId());

        assertThat(friends)
                .hasSize(1)
                .containsAll(List.of(userDb2));

        dbFriendsStorage.deleteFriend(userDb1.getId(), userDb2.getId());
        friends = dbFriendsStorage.getFriends(userDb1.getId());

        assertThat(friends)
                .isEmpty();
    }

    @Test
    public void testAddFriendUserNotExist() {
        assertThatThrownBy(() -> {
            dbFriendsStorage.addFriend(-1, 1);
        }).isInstanceOf(NotFoundException.class).hasMessage(EnumOfExceptions.UPDATE_FRIEND.getExp());
    }

    @Test
    public void testDeleteFriendUserNotExist() {
        assertThatThrownBy(() -> {
            dbFriendsStorage.deleteFriend(1, -1);
        }).isInstanceOf(NotFoundException.class).hasMessage(EnumOfExceptions.DELETE_FRIEND.getExp());
    }

    @Test
    public void testCommonFriends() {
        User user1 = User.builder()
                .email("user1@example.com")
                .login("login1")
                .name("user1")
                .birthday(LocalDate.of(2000, 1, 1))
                .build();
        User user2 = User.builder()
                .email("user_updated@example.com")
                .login("login_updated")
                .name("user_updated")
                .birthday(LocalDate.of(2000, 1, 1))
                .build();
        User user3 = User.builder()
                .email("user_updated3@example.com")
                .login("login_updated3")
                .name("user_updated3")
                .birthday(LocalDate.of(2000, 1, 1))
                .build();

        User userDb1 = dbUserStorage.create(user1);
        User userDb2 = dbUserStorage.create(user2);
        User userDb3 = dbUserStorage.create(user3);

        dbFriendsStorage.addFriend(userDb1.getId(), userDb3.getId());
        dbFriendsStorage.addFriend(userDb2.getId(), userDb3.getId());
        dbFriendsStorage.addFriend(userDb3.getId(), userDb1.getId());
        dbFriendsStorage.addFriend(userDb3.getId(), userDb2.getId());

        Collection<User> commonFriends = dbFriendsStorage.getCommonFriends(1, 2);

        assertThat(commonFriends)
                .hasSize(1)
                .containsAll(List.of(userDb3));
    }

}
