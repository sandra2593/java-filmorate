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
public class DbUserStorageTest {
    private final DbUserStorage dbUserStorage;

    @Test
    public void testCreateUser() {
        User user = User.builder()
                .email("email1@example.com")
                .login("login1")
                .name("user1")
                .birthday(LocalDate.of(2023, 4, 1))
                .build();

        User createdUser = dbUserStorage.create(user);

        assertThat(createdUser)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("email", "email1@example.com")
                .hasFieldOrPropertyWithValue("login", "login1")
                .hasFieldOrPropertyWithValue("name", "user1")
                .hasFieldOrPropertyWithValue("birthday", LocalDate.of(2023, 4, 1));
    }

    @Test
    public void testUpdateUser() {
        User user = User.builder()
                .email("email@example.com")
                .login("login")
                .name("user")
                .birthday(LocalDate.of(2023, 4, 1))
                .build();
        User newUser = User.builder()
                .id(1)
                .email("emailUpd@example.com")
                .login("loginUpd")
                .name("userUpd")
                .birthday(LocalDate.of(2023, 4, 1))
                .build();

        dbUserStorage.create(user);
        User updatedUser = dbUserStorage.update(newUser);

        assertThat(updatedUser)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("email", "emailUpd@example.com")
                .hasFieldOrPropertyWithValue("login", "loginUpd")
                .hasFieldOrPropertyWithValue("name", "userUpd")
                .hasFieldOrPropertyWithValue("birthday", LocalDate.of(2023, 4, 1));
    }

    @Test
    public void testUpdateUserNotExist() {
        User user = User.builder()
                .email("email@example.com")
                .login("login")
                .name("user")
                .birthday(LocalDate.of(2023, 4, 1))
                .build();
        User newUser = User.builder()
                .id(-1)
                .email("emailUpd@example.com")
                .login("loginUpd")
                .name("userUpd")
                .birthday(LocalDate.of(2023, 4, 1))
                .build();

        dbUserStorage.create(user);

        assertThatThrownBy(() -> {
            dbUserStorage.update(newUser);
        }).isInstanceOf(NotFoundException.class).hasMessage(EnumOfExceptions.NO_USER.getExp());
    }

    @Test
    public void testAllUsers() {
        User user = User.builder()
                .email("email@example.com")
                .login("login")
                .name("user")
                .birthday(LocalDate.of(2023, 4, 1))
                .build();

        User userDb = dbUserStorage.create(user);
        Collection<User> allUsers = dbUserStorage.findAll();

        assertThat(allUsers)
                .hasSize(1)
                .containsAll(List.of(userDb));
    }
}
