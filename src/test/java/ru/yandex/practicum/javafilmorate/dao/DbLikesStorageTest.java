package ru.yandex.practicum.javafilmorate.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.javafilmorate.exception.EnumOfExceptions;
import ru.yandex.practicum.javafilmorate.exception.NotFoundException;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.model.Rating;
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
public class DbLikesStorageTest {
    private final DbLikesStorage dbLikesStorage;
    private final DbFilmStorage dbFilmStorage;
    private final DbUserStorage dbUserStorage;

    @Test
    public void testAddDeleteLikeAndCheckTop() {
        User user1 = User.builder()
                .email("email1@example.com")
                .login("login1")
                .name("user1")
                .birthday(LocalDate.of(2023, 4, 1))
                .build();
        User user2 = User.builder()
                .email("email2@example.com")
                .login("login2")
                .name("user2")
                .birthday(LocalDate.of(2023, 4, 1))
                .build();
        Film film1 = Film.builder()
                .name("film1")
                .description("description1")
                .releaseDate(LocalDate.of(2023, 4, 1))
                .duration(1)
                .mpa(Rating.builder().id(1).name("G").build())
                .genres(List.of())
                .build();
        Film film2 = Film.builder()
                .name("film2")
                .description("description2")
                .releaseDate(LocalDate.of(2023, 4, 1))
                .duration(10)
                .mpa(Rating.builder().id(1).name("G").build())
                .genres(List.of())
                .build();

        User createdUser1 = dbUserStorage.create(user1);
        User createdUser2 = dbUserStorage.create(user2);
        Film createdFilm1 = dbFilmStorage.create(film1);
        Film createdFilm2 = dbFilmStorage.create(film2);

        dbLikesStorage.addLike(createdFilm2.getId(), createdUser1.getId());
        dbLikesStorage.addLike(createdFilm2.getId(), createdUser2.getId());

        Collection<Film> likedFilms = dbFilmStorage.getTopFilms(1);

        // проверка добавления лайка и топ
        assertThat(likedFilms)
                .hasSize(1)
                .containsSequence(film2);

        dbLikesStorage.addLike(createdFilm1.getId(), createdUser1.getId());
        dbLikesStorage.deleteLike(createdFilm2.getId(), createdUser1.getId());
        dbLikesStorage.deleteLike(createdFilm2.getId(), createdUser2.getId());

        likedFilms = dbFilmStorage.getTopFilms(1);

        // проверка удаления лайка и топ
        assertThat(likedFilms)
                .hasSize(1)
                .containsSequence(film1);
    }

    @Test
    public void testAddLikeFilmNotExist() {
        assertThatThrownBy(() -> {
            dbLikesStorage.addLike(-1, 1);
        }).isInstanceOf(NotFoundException.class).hasMessage(EnumOfExceptions.UPDATE_LIKES.getExp());
    }

    @Test
    public void testDeleteLikeUserNotExist() {
        assertThatThrownBy(() -> {
            dbLikesStorage.deleteLike(1, -1);
        }).isInstanceOf(NotFoundException.class).hasMessage(EnumOfExceptions.DELETE_LIKES.getExp());
    }

}
