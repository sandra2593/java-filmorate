package ru.yandex.practicum.javafilmorate.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.javafilmorate.exception.EnumOfExceptions;
import ru.yandex.practicum.javafilmorate.exception.NotFoundException;
import ru.yandex.practicum.javafilmorate.model.Genre;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class DbGenreStorageTest {
    private final DbGenreStorage dbGenreStorage;
    private Collection<Genre> dGenres = List.of(
            Genre.builder().id(1).name("Комедия").build(),
            Genre.builder().id(2).name("Драма").build(),
            Genre.builder().id(3).name("Мультфильм").build(),
            Genre.builder().id(4).name("Триллер").build(),
            Genre.builder().id(5).name("Документальный").build(),
            Genre.builder().id(6).name("Боевик").build()
    );

    @Test
    public void testGenreList() {
        Collection<Genre> dbGenres = dbGenreStorage.getGenres();

        assertThat(dbGenres)
                .hasSize(6)
                .containsAll(dGenres);
    }

    @Test
    public void testRatingIdNotExisting() {
        assertThatThrownBy(() -> {
            dbGenreStorage.getGenreById(-1);
        }).isInstanceOf(NotFoundException.class).hasMessage(EnumOfExceptions.NO_GENRE.getExp());
    }
}
