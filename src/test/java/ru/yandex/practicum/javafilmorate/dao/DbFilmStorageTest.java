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
import ru.yandex.practicum.javafilmorate.model.Genre;
import ru.yandex.practicum.javafilmorate.model.Rating;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class DbFilmStorageTest {
    private final DbFilmStorage dbFilmStorage;

    @Test
    public void testCreateFilm() {
        Film film = Film.builder()
                .name("film")
                .description("description")
                .releaseDate(LocalDate.of(2023, 4, 1))
                .duration(1)
                .genres(
                        List.of(Genre.builder().id(1).name("Комедия").build())
                )
                .mpa(Rating.builder().id(1).build())
                .build();

        Film createdFilm = dbFilmStorage.create(film);

        assertThat(createdFilm)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("name", "film")
                .hasFieldOrPropertyWithValue("description", "description")
                .hasFieldOrPropertyWithValue("releaseDate", LocalDate.of(2023, 4, 1))
                .hasFieldOrPropertyWithValue("duration", 1)
                .hasFieldOrPropertyWithValue("mpa", Rating.builder().id(1).name("G").build())
                .hasFieldOrPropertyWithValue("genres", List.of(Genre.builder().id(1).name("Комедия").build()));
    }

    @Test
    public void testUpdateFilmDeleteGenresChangeRating() {
        Film existingFilm = Film.builder()
                .name("film")
                .description("description")
                .releaseDate(LocalDate.of(2023, 4, 1))
                .duration(1)
                .mpa(Rating.builder().id(1).build())
                .genres(
                        List.of(Genre.builder().id(1).name("Комедия").build())
                )
                .build();

        Film newFilm = Film.builder()
                .id(1)
                .name("filmUpd")
                .description("description")
                .releaseDate(LocalDate.of(2023, 4, 1))
                .duration(1)
                .mpa(Rating.builder().id(4).build())
                .genres(List.of())
                .build();

        dbFilmStorage.create(existingFilm);
        Film updatedFilm = dbFilmStorage.update(newFilm);

        assertThat(updatedFilm)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("name", "filmUpd")
                .hasFieldOrPropertyWithValue("description", "description")
                .hasFieldOrPropertyWithValue("releaseDate", LocalDate.of(2023, 4, 1))
                .hasFieldOrPropertyWithValue("duration", 1)
                .hasFieldOrPropertyWithValue("mpa", Rating.builder().id(4).name("R").build())
                .hasFieldOrPropertyWithValue("genres", List.of());
    }

    @Test
    public void testUpdateFilmNotExist() {
        Film existingFilm = Film.builder()
                .name("film")
                .description("description")
                .releaseDate(LocalDate.of(2023, 4, 1))
                .duration(10)
                .mpa(Rating.builder().id(1).name("G").build())
                .genres(List.of())
                .build();

        Film newFilm = Film.builder()
                .id(-1)
                .name("film")
                .description("description")
                .releaseDate(LocalDate.of(2023, 4, 1))
                .duration(10)
                .mpa(Rating.builder().id(1).name("G").build())
                .genres(List.of())
                .build();

        dbFilmStorage.create(existingFilm);

        assertThatThrownBy(() -> {
            dbFilmStorage.update(newFilm);
        }).isInstanceOf(NotFoundException.class).hasMessage(EnumOfExceptions.NO_FILM.getExp());
    }

    @Test
    public void testAllFilms() {
        Film film = Film.builder()
                .name("film")
                .description("description")
                .releaseDate(LocalDate.of(2023, 4, 1))
                .duration(10)
                .mpa(Rating.builder().id(1).name("G").build())
                .genres(List.of())
                .build();

        Collection<Film> films = List.of(film);
        dbFilmStorage.create(film);
        Collection<Film> filmDb = dbFilmStorage.findAll();

        assertThat(filmDb)
                .hasSize(1)
                .containsAll(films);
    }
}
